package com.gopay.membership.adapter.out.persistence;

import com.gopay.membership.domain.Membership;
import org.springframework.stereotype.Component;

@Component
public class MembershipMapper {

    public  Membership mapToDomainEntity(MembershipEntity membershipEntity) {
        return Membership.generateMember(
                new Membership.MembershipId(membershipEntity.getMembershipId() + ""),
                new Membership.MembershipName(membershipEntity.getName()),
                new Membership.MembershipEmail(membershipEntity.getEmail()),
                new Membership.MembershipAddress(membershipEntity.getAddress()),
                new Membership.MembershipIsValid(membershipEntity.isValid()),
                new Membership.MembershipIsCorp(membershipEntity.isCorp())
        );
    }
}
