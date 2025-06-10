package com.gopay.banking.adapter.out.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gopay.banking.application.port.out.GetMemBershipPort;
import com.gopay.banking.application.port.out.Membership;
import com.gopay.common.CommonHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MembershipServiceAdapter implements GetMemBershipPort {

    private final CommonHttpClient commonHttpClient;

    private final String membershipServiceUrl;

    public MembershipServiceAdapter(CommonHttpClient commonHttpClient,
                                    @Value("${service.membership.url}") String membershipServiceUrl) {
        this.commonHttpClient = commonHttpClient;
        this.membershipServiceUrl = membershipServiceUrl;
    }

    @Override
    public MembershipStatus getMemBership(String membershipId) {

        // 실제로 http call
        // we need http client

        String url = String.join("/", membershipServiceUrl, "membership",membershipId);

        try{
            String jsonResponse = commonHttpClient.sendGetRequest(url).body();
            // json Membership

            ObjectMapper mapper = new ObjectMapper();
            Membership membership = mapper.readValue(jsonResponse, Membership.class);

            if (membership.isValid() ) {
                return new MembershipStatus(membership.getMemberShipId(), true);
            } else {
                return new MembershipStatus(membership.getMemberShipId(), false);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }




    }
}
