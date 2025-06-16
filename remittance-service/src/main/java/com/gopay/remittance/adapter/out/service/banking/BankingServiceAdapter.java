package com.gopay.remittance.adapter.out.service.banking;

import com.gopay.common.CommonHttpClient;
import com.gopay.common.ExternalSystemAdapter;
import com.gopay.remittance.application.port.out.Banking.BankingInfo;
import com.gopay.remittance.application.port.out.Banking.BankingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;


@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankingServiceAdapter implements BankingPort {

    private final CommonHttpClient commonHttpClient;

    @Value("${service.banking.url}")
    private String serviceUrl;


    @Override
    public BankingInfo getMembershipBankingInfo(String bankName, String bankAccountNumber) {
        return null;
    }

    @Override
    public boolean requestFirmbanking(String bankName, String bankAccountNumber, int amount) {
        return false;
    }
}
