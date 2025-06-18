package com.gopay.banking.adapter.out.persistence;

import com.gopay.banking.application.port.in.GetRegisteredBankAccountCommand;
import com.gopay.banking.application.port.out.GetRegisteredBankAccountPort;
import com.gopay.banking.application.port.out.RegisterBankAccountPort;
import com.gopay.banking.domain.RegisteredBankAccount;
import com.gopay.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisteredBankAccountPersistenceAdapter implements RegisterBankAccountPort, GetRegisteredBankAccountPort {

    private final RegisteredBankAccountRepository bankAccountRepository;

    @Override
    public RegisteredBankAccountEntity createRegisteredBankAccount(RegisteredBankAccount.MembershipId membershipId, RegisteredBankAccount.BankName bankName, RegisteredBankAccount.BankAccountNumber bankAccountNumber, RegisteredBankAccount.LinkedStatusIsValid linkedStatusIsValid, RegisteredBankAccount.AggregateIdentifier aggregateIdentifier) {
        return bankAccountRepository.save(
                new RegisteredBankAccountEntity(
                        membershipId.getMembershipId(),
                        bankName.getBankName(),
                        bankAccountNumber.getBankAccountNumber(),
                        linkedStatusIsValid.isLinkedStatusIsValid(),
                        aggregateIdentifier.getAggregateIdentifier()
                )
        );
    }

    @Override
    public RegisteredBankAccountEntity getRegisteredBankAccount(GetRegisteredBankAccountCommand command) {
        List<RegisteredBankAccountEntity> entityList = bankAccountRepository.findByMembershipId(command.getMembershipId());
        if (!entityList.isEmpty()) {
            return entityList.get(0);
        }
        return null;
    }
}
