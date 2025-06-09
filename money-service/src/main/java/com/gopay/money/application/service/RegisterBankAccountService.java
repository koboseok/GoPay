package com.gopay.money.application.service;


import com.gopay.common.UseCase;
import com.gopay.money.adapter.out.persistence.MemberMoneyEntity;
import com.gopay.money.adapter.out.persistence.MoneyChangingRequestMapper;
import com.gopay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.gopay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.gopay.money.application.port.out.IncreaseMoneyPort;
import com.gopay.money.domain.MemberMoney;
import com.gopay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterBankAccountService implements IncreaseMoneyRequestUseCase {

    private final IncreaseMoneyPort registerBankAccountPort;
    private final MoneyChangingRequestMapper mapper;
    private final IncreaseMoneyPort increaseMoneyPort;


    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {

        // 머니의 증액(충전) 과정
        // 1. 고객 정보가 정상인지 확인
        // 멤버 서비스 조회

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
}


