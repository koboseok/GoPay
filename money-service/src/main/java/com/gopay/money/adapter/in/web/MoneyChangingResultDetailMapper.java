package com.gopay.money.adapter.in.web;

import com.gopay.money.domain.MoneyChangingRequest;
import org.springframework.stereotype.Component;

@Component
public class MoneyChangingResultDetailMapper {

    public static MoneyChangingResultDetail mapFromMoneyChangingResult(MoneyChangingRequest request) {

        return new MoneyChangingResultDetail(
                request.getMoneyChangingRequestId(),
                request.getMoneyChangingType(),
                request.getChangingMoneyStatus(),
                request.getChangingMoneyAmount()

        );
    }
}
