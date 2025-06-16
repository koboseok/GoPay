package com.gopay.remittance.adapter.out.service.membership;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gopay.common.CommonHttpClient;
import com.gopay.common.ExternalSystemAdapter;
import com.gopay.remittance.application.port.out.membership.MemBershipPort;
import com.gopay.remittance.application.port.out.membership.MembershipStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;



@ExternalSystemAdapter
@RequiredArgsConstructor
public class MembershipServiceAdapter implements MemBershipPort {


    private final CommonHttpClient commonHttpClient;

    @Value("${service.membership.url}")
    private String membershipServiceUrl;



    @Override
    public MembershipStatus getMembershipStatus(String membershipId) {

        // 실제로 http call
        // we need http client

        String url = String.join("/", this.membershipServiceUrl, "membership",membershipId);

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
