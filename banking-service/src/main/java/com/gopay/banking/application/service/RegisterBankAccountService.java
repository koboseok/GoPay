package com.gopay.banking.application.service;

import com.gopay.banking.adapter.axon.command.CreateRegisteredBankAccountCommand;
import com.gopay.banking.adapter.out.external.bank.BankAccount;
import com.gopay.banking.adapter.out.external.bank.GetBankAccountRequest;
import com.gopay.banking.adapter.out.persistence.RegisteredBankAccountEntity;
import com.gopay.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import com.gopay.banking.adapter.out.service.MembershipStatus;
import com.gopay.banking.application.port.in.GetRegisteredBankAccountCommand;
import com.gopay.banking.application.port.in.GetRegisteredBankAccountUseCase;
import com.gopay.banking.application.port.in.RegisterBankAccountCommand;
import com.gopay.banking.application.port.in.RegisterBankAccountUseCase;
import com.gopay.banking.application.port.out.GetMemBershipPort;
import com.gopay.banking.application.port.out.GetRegisteredBankAccountPort;
import com.gopay.banking.application.port.out.RegisterBankAccountPort;
import com.gopay.banking.application.port.out.RequestBankAccountInfoPort;
import com.gopay.banking.domain.RegisteredBankAccount;
import com.gopay.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterBankAccountService implements RegisterBankAccountUseCase, GetRegisteredBankAccountUseCase {

    private final GetMemBershipPort getMemBershipPort;
    private final RegisterBankAccountPort registerBankAccountPort;
    private final RegisteredBankAccountMapper mapper;
    private final RequestBankAccountInfoPort requestBankAccountInfoPort;
    private final GetRegisteredBankAccountPort getRegisteredBankAccountPort;
    private final CommandGateway commandGateway;


    @Override
    public RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command) {
        // 은행 계좌를 등록해야하는 서비스
        // membership service check
        // membershipId로 membership-service 호출하여 member가 유효한지 확인
        MembershipStatus membershipStatus = getMemBershipPort.getMemBership(command.getMembershipId());
        if (!membershipStatus.isValid()) {
            return null;
        }

        // 1. 등록된 계좌인지 확인한다.
        // 외부의 은행에 이 계좌 정상인지? 확인
        // biz logic -> external system
        // port -> adapter -> external system
        // 실제 외부의 은행계좌 정보를 get
        BankAccount accountInfo = requestBankAccountInfoPort.getBankAccountInfo(new GetBankAccountRequest(command.getBankName(), command.getBankAccountNumber()));

        // 2. 등록가능한 계좌라면, 등록한다. 성공하면, 등록에 성공한 등록 정보를 리턴
        // 2-1. 등록가능하지 않은 계좌라면. 에러를 러턴
        if (accountInfo.isValid()) {

            // 등록 정보 저장
            RegisteredBankAccountEntity savaAccountInfo = registerBankAccountPort.createRegisteredBankAccount(
                    new RegisteredBankAccount.MembershipId(command.getMembershipId()),
                    new RegisteredBankAccount.BankName(command.getBankName()),
                    new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber()),
                    new RegisteredBankAccount.LinkedStatusIsValid(command.isLinkedStatusIsValid()),
                    new RegisteredBankAccount.AggregateIdentifier("")

            );

            return mapper.mapToDomainEntity(savaAccountInfo);

        } else {

            return null;
        }



    }


    @Override
    public void registerBankAccountByEvent(RegisterBankAccountCommand command) {
        commandGateway.send(new CreateRegisteredBankAccountCommand(command.getMembershipId(), command.getBankName(), command.getBankAccountNumber()))
                .whenComplete(
                        (result, throwable) -> {
                            if(throwable != null) {
                                throwable.printStackTrace();
                            } else {
                                // 정상적으로 이벤트 소싱.
                                // -> registeredBankAccount 를 insert
                                registerBankAccountPort.createRegisteredBankAccount(
                                        new RegisteredBankAccount.MembershipId(command.getMembershipId()+""),
                                        new RegisteredBankAccount.BankName(command.getBankName()),
                                        new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber()),
                                        new RegisteredBankAccount.LinkedStatusIsValid(command.isLinkedStatusIsValid()),
                                        new RegisteredBankAccount.AggregateIdentifier(result.toString()));
                            }
                        }
                );
    }

    @Override
    public RegisteredBankAccount getRegisteredBankAccount(GetRegisteredBankAccountCommand command) {
        return mapper.mapToDomainEntity(getRegisteredBankAccountPort.getRegisteredBankAccount(command));
    }
}
