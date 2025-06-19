package com.gopay.money.aggregation.adapter.out.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gopay.common.CommonHttpClient;
import com.gopay.common.ExternalSystemAdapter;
import com.gopay.money.aggregation.application.port.out.GetMoneySumPort;
import com.gopay.money.aggregation.application.port.out.MemberMoney;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@ExternalSystemAdapter
public class MoneyServiceAdapter implements GetMoneySumPort {

    private final CommonHttpClient moneyServiceHttpClient;

    @Value("${service.money.url}")
    private String moneyServiceEndpoint;

    public MoneyServiceAdapter(CommonHttpClient commonHttpClient,
                                    @Value("${service.money.url}") String moneyServiceEndpoint) {
        this.moneyServiceHttpClient = commonHttpClient;
        this.moneyServiceEndpoint = moneyServiceEndpoint;
    }
    @Override
    public List<MemberMoney> getMoneySumByMembershipIds(List<String> membershipIds) {

        String url = String.join("/", moneyServiceEndpoint, "money/member-money");
        ObjectMapper mapper = new ObjectMapper();

        try {

            FindMemberMoneyRequest request = new FindMemberMoneyRequest(membershipIds);
            String jsonResponse = moneyServiceHttpClient.sendPostRequest(url, mapper.writeValueAsString(request)).get().body();

            // n명 고객 / 각 고객의 MemberMoney를 가져온다.

            List<MemberMoney> memberMoneyList = mapper.readValue(jsonResponse, new TypeReference<>() {});

            return memberMoneyList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
