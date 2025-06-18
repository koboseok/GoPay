package com.gopay.banking.application.port.out;

import com.gopay.banking.adapter.out.persistence.RegisteredBankAccountEntity;
import com.gopay.banking.application.port.in.GetRegisteredBankAccountCommand;

public interface GetRegisteredBankAccountPort {
    RegisteredBankAccountEntity getRegisteredBankAccount(GetRegisteredBankAccountCommand command);
}