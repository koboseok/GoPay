package com.gopay.money.application.port.in;


import com.gopay.common.UseCase;
import com.gopay.money.domain.MoneyChangingRequest;


@UseCase
public interface IncreaseMoneyRequestUseCase {
    // 비즈니스 로직 정의

    MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);
    MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command);
}
