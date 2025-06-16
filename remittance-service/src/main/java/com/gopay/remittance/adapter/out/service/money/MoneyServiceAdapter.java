package com.gopay.remittance.adapter.out.service.money;

import com.gopay.common.CommonHttpClient;
import com.gopay.common.ExternalSystemAdapter;
import com.gopay.remittance.application.port.out.money.MoneyInfo;
import com.gopay.remittance.application.port.out.money.MoneyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;


@ExternalSystemAdapter
@RequiredArgsConstructor
public class MoneyServiceAdapter implements MoneyPort {

    private final CommonHttpClient commonHttpClient;

    @Value("${service.money.url}")
    private String serviceUrl;


    @Override
    public MoneyInfo getMoneyInfo(String membershipId) {
        return null;
    }

    @Override
    public boolean requestMoneyRecharging(String membershipId, int amount) {
        return false;
    }

    @Override
    public boolean requestMoneyIncrease(String membershipId, int amount) {
        return false;
    }

    @Override
    public boolean requestMoneyDecrease(String membershipId, int amount) {
        return false;
    }
}
