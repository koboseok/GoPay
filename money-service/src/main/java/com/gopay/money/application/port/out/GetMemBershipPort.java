package com.gopay.money.application.port.out;


import com.gopay.money.adapter.out.service.MembershipStatus;

public interface GetMemBershipPort {


    MembershipStatus getMemBership(String membershipId);

}
