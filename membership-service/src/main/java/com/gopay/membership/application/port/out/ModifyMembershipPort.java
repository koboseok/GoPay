package com.gopay.membership.application.port.out;

import com.gopay.membership.adapter.out.persistence.MembershipEntity;
import com.gopay.membership.domain.Membership;

public interface ModifyMembershipPort {

    MembershipEntity modifyMembership(
            Membership.MembershipId membershipId,
            Membership.MembershipName membershipName
            , Membership.MembershipEmail membershipEmail
            , Membership.MembershipAddress membershipAddress
            , Membership.MembershipIsValid membershipIsValid
            , Membership.MembershipIsCorp membershipIsCorp
    );
}
