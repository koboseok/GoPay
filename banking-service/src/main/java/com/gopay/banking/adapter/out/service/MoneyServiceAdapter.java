package com.gopay.banking.adapter.out.service;

import com.gopay.common.CommonHttpClient;
import com.gopay.common.ExternalSystemAdapter;
import lombok.RequiredArgsConstructor;


@ExternalSystemAdapter
@RequiredArgsConstructor
public class MoneyServiceAdapter {

    private final CommonHttpClient commonHttpClient;


}
