package com.gopay.banking.application.port.out;


import com.gopay.banking.adapter.out.service.MembershipStatus;

public interface GetMemBershipPort {


    MembershipStatus getMemBership(String membershipId);

}
