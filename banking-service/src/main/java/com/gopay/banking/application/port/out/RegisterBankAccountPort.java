package com.gopay.banking.application.port.out;

import com.gopay.banking.adapter.out.persistence.RegisteredBankAccountEntity;
import com.gopay.banking.domain.RegisteredBankAccount;


public interface RegisterBankAccountPort {

    RegisteredBankAccountEntity createRegisteredBankAccount(
            RegisteredBankAccount.MembershipId membershipId,
            RegisteredBankAccount.BankName bankName,
            RegisteredBankAccount.BankAccountNumber bankAccountNumber,
            RegisteredBankAccount.LinkedStatusIsValid  linkedStatusIsValid,
            RegisteredBankAccount.AggregateIdentifier aggregateIdentifier
    );
}
