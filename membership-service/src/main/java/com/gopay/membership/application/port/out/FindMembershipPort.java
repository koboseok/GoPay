package com.gopay.membership.application.port.out;

import com.gopay.membership.adapter.out.persistence.MembershipEntity;
import com.gopay.membership.domain.Membership;

import java.util.List;

public interface FindMembershipPort {

    MembershipEntity findMembership(Membership.MembershipId membershipId);

    List<MembershipEntity> findMembershipListByAddress(Membership.MembershipAddress membershipAddress);
}
