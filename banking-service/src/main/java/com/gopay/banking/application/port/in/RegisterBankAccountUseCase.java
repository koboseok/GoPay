package com.gopay.banking.application.port.in;

import com.gopay.banking.domain.RegisteredBankAccount;
import com.gopay.common.UseCase;


@UseCase
public interface RegisterBankAccountUseCase {
    // 비즈니스 로직 정의

    RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command);
}
