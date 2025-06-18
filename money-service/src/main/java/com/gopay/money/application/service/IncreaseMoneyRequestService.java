package com.gopay.money.application.service;


import com.gopay.common.CountDownLatchManager;
import com.gopay.common.RechargingMoneyTask;
import com.gopay.common.SubTask;
import com.gopay.common.UseCase;
import com.gopay.money.adapter.axon.command.MemberMoneyCreatedCommand;
import com.gopay.money.adapter.axon.command.RechargingMoneyRequestCreateCommand;
import com.gopay.money.adapter.out.persistence.MemberMoneyEntity;
import com.gopay.money.adapter.out.persistence.MoneyChangingRequestMapper;
import com.gopay.money.application.port.in.*;
import com.gopay.money.application.port.out.GetMembershipPort;
import com.gopay.money.application.port.out.IncreaseMoneyPort;
import com.gopay.money.application.port.out.SendRechargingMoneyTaskPort;
import com.gopay.money.domain.MemberMoney;
import com.gopay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@UseCase
@RequiredArgsConstructor
@Transactional
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase, CreateMemberMoneyUseCase {

    private final CountDownLatchManager countDownLatchManager;
    private final GetMembershipPort getMemBershipPort;
    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;
    private final IncreaseMoneyPort registerBankAccountPort;
    private final MoneyChangingRequestMapper mapper;
    private final IncreaseMoneyPort increaseMoneyPort;

    private final CommandGateway commandGateway; // axon

    private final CreateMemberMoneyPort createMemberMoneyPort;
    private final GetMemberMoneyPort getMemberMoneyPort;


    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {

        // 머니의 증액(충전) 과정
        // 1. 고객 정보가 정상인지 확인
        // 멤버 서비스 조회
        getMemBershipPort.getMemBership(command.getTargetMembershipId());
        // 2. 고객의 연동된 계좌가 있는지 (정상인지), 고객의 연동된 계좌의 잔액이 충분한지
        // 뱅킹 서비스 조회

        // 3. 페이(법인) 게좌 상태도 정상인지 (입출금이 가능한 상태인지)
        // 뱅킹 서비스 조회

        // 4. 증액을 위한 "기록" / 요청 상태로 MoneyChangingRequest를 생성 (MoneyChangingRequest)


        // 5. 펌뱅킹을 수행하고 ( 고객의 연동된 계좌 -> 페이 법인 계좌) (뱅킹)


        // 6-1. 결과가 정상 -> 성공으로 MoneyChangingRequest 상태값을 변동 후에 리턴
        // 성공 시에 멤버의 MemberMoney 값 증액이 필요
        MemberMoneyEntity memberMoneyEntity = increaseMoneyPort.increaseMoney(new MemberMoney.MembershipId(command.getTargetMembershipId()), command.getAmount());

        // 6-2. 결과가 실패 -> 실패라고 MoneyChangingRequest 상태값을 변동 후에 리턴
        return mapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
                        new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                        new MoneyChangingRequest.MoneyChangingType(0),
                        new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                        new MoneyChangingRequest.MoneyChangingResultStatus(1),
                        new MoneyChangingRequest.Uuid(UUID.randomUUID().toString())
                )
        );

    }


    @Override
    public MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command) {


        // 각 서비스에 특정 membershipId로 Validation을 하기위한 Task로 가정한다.
        // 1. Subtask, Task
        SubTask validMembershipTask = SubTask.builder()
                .subTaskName("Valid Membership Task:" + "멤버십 유효성 검사")
                .membershipId(command.getTargetMembershipId())
                .taskType("membership")
                .status("ready")
                .build();

        // Banking subtask
        // Banking Account Validation
        SubTask validBankingTask = SubTask.builder()
                .subTaskName("Valid Banking Task:" + "뱅킹 계좌 유효성 검사")
                .membershipId(command.getTargetMembershipId())
                .taskType("banking")
                .status("ready")
                .build();
        // Amount Money Firmbanking --> 무조건 ok 받았다고 가정한다.


        List<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(validMembershipTask);
        subTaskList.add(validBankingTask);

        RechargingMoneyTask task = RechargingMoneyTask.builder()
                .taskId(UUID.randomUUID().toString())
                .taskName("Increase Money Task: " + "머니 충전 Task")
                .subTaskList(subTaskList)
                .moneyAmount(command.getAmount())
                .membershipId(command.getTargetMembershipId())
                .toBankName("gopay")
                .build();

        // 2. Kafka Cluster Produce
        // Task Produce
        // 프로듀스된 서브테스크의 리스트를 테스크 컨슈머가 수행
        sendRechargingMoneyTaskPort.sendRechargingMoneyTask(task);
        countDownLatchManager.addCountDownLatch(task.getTaskId());

        // 수행하는 동안 countDownLatchManager를 통해 스레드를 강제로 기다리게 만든다
        // 3. Wait
        try {
            countDownLatchManager.getCountDownLatch(task.getTaskId()).await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 기다리는 동안
        // 3-1. task-consumer
        // 등록된 sub-task, status 모두 ok -> task 결과를 Produce

        // 4. Task Result Consume
        // 받은 응답을 다시, countDownLatchManager를 통해서 결과 데이터를 받는다.
        String result = countDownLatchManager.getDataForKey(task.getTaskId());
        if (result.equals("success")) {
            // 4-1. consume ok logic
            MemberMoneyEntity memberMoneyEntity = increaseMoneyPort.increaseMoney(
                    new MemberMoney.MembershipId(command.getTargetMembershipId()),
                    command.getAmount());

            if (memberMoneyEntity != null) {
                return mapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
                        new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                        new MoneyChangingRequest.MoneyChangingType(1),
                        new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                        new MoneyChangingRequest.MoneyChangingResultStatus(1),
                        new MoneyChangingRequest.Uuid(UUID.randomUUID().toString())
                ));
            }
        } else {
            // 4-1. consume fail logic
            return null;
        }

        // 5. Consume ok, Logic
        return null;
    }


    @Override
    public void createMemberMoney(CreateMemberMoneyCommand command) {
        MemberMoneyCreatedCommand axonCommand = new MemberMoneyCreatedCommand(command.getMembershipId());
        commandGateway.send(axonCommand).whenComplete((result, throwable) -> {
            if (throwable != null) {
                System.out.println("throwable = " + throwable);
                throw new RuntimeException(throwable);
            } else{
                System.out.println("result = " + result);
                createMemberMoneyPort.createMemberMoney(
                        new MemberMoney.MembershipId(command.getMembershipId()),
                        new MemberMoney.MoneyAggregateIdentifier(result.toString())
                );
            }
        });
    }


    @Override
    public void increaseMoneyRequestByEvent(IncreaseMoneyRequestCommand command) {

        MemberMoneyEntity memberMoneyJpaEntity = getMemberMoneyPort.getMemberMoney(new MemberMoney.MembershipId(command.getTargetMembershipId()));
        String memberMoneyAggregateIdentifier = memberMoneyJpaEntity.getAggregateIdentifier();

        // Saga 의 시작을 나타내는 커맨드!
        // RechargingMoneyRequestCreateCommand
        // member money와 통신
        commandGateway.send(new RechargingMoneyRequestCreateCommand(memberMoneyAggregateIdentifier,
                UUID.randomUUID().toString(),
                command.getTargetMembershipId(),
                command.getAmount())
        ).whenComplete(
                (result, throwable) -> {
                    if (throwable != null) {
                        throwable.printStackTrace();
                        throw new RuntimeException(throwable);
                    } else {
                        System.out.println("result = " + result); // aggregateIdentifier
                    }
                }
        );

//        MemberMoneyJpaEntity memberMoneyJpaEntity = getMemberMoneyPort.getMemberMoney(
//                new MemberMoney.MembershipId(command.getTargetMembershipId())
//        );
//
//        String aggregateIdentifier = memberMoneyJpaEntity.getAggregateIdentifier();
//        // command
//        commandGateway.send(IncreaseMemberMoneyCommand.builder()
//                        .aggregateIdentifier(aggregateIdentifier)
//                        .membershipId(command.getTargetMembershipId())
//                        .amount(command.getAmount()).build())
//        .whenComplete(
//                (result, throwable) -> {
//                    if (throwable != null) {
//                        throwable.printStackTrace();
//                        throw new RuntimeException(throwable);
//                    } else {
//                        // Increase money -> money incr
//                        System.out.println("increaseMoney result = " + result);
//                        increaseMoneyPort.increaseMoney(
//                                new MemberMoney.MembershipId(command.getTargetMembershipId())
//                                , command.getAmount());
//                    }
//                }
//        );
    }
}


