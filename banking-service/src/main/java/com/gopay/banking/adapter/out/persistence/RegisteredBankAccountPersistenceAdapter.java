package com.gopay.banking.adapter.out.persistence;

import com.gopay.banking.application.port.out.RegisterBankAccountPort;
import com.gopay.banking.domain.RegisteredBankAccount;
import com.gopay.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisteredBankAccountPersistenceAdapter implements RegisterBankAccountPort {

    private final RegisteredBankAccountRepository registeredBankAccountRepository;

    @Override
    public RegisteredBankAccountEntity createRegisteredBankAccount(RegisteredBankAccount.MembershipId membershipId, RegisteredBankAccount.BankName bankName, RegisteredBankAccount.BankAccountNumber bankAccountNumber, RegisteredBankAccount.LinkedStatusIsValid linkedStatusIsValid) {


        return registeredBankAccountRepository.save(
                new RegisteredBankAccountEntity(
                        membershipId.getMembershipId(),
                        bankName.getBankName(),
                        bankAccountNumber.getBankAccountNumber(),
                        linkedStatusIsValid.isLinkedStatusIsValid()
                )
        );

    }
}
