package com.gopay.money.application.port.out;


import com.gopay.money.adapter.out.service.MembershipStatus;

public interface GetMembershipPort {


    MembershipStatus getMemBership(String membershipId);

}
