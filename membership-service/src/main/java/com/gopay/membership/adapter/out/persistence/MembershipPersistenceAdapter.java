package com.gopay.membership.adapter.out.persistence;

import com.gopay.membership.application.port.out.FindMembershipPort;
import com.gopay.membership.application.port.out.RegisterMembershipPort;
import com.gopay.membership.domain.Membership;
import common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort {

    private final MembershipRepository membershipRepository;

    @Override
    public MembershipEntity createMembership(Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp) {

        return membershipRepository.save(
                new MembershipEntity(
                        membershipName.getNameValue(),
                        membershipEmail.getEmailValue(),
                        membershipAddress.getAddressValue(),
                        membershipIsValid.isValidValue(),
                        membershipIsCorp.isCorp()
                )
        );
    }

    @Override
    public MembershipEntity findMembership(Membership.MembershipId membershipId) {
        return membershipRepository.getById(Long.parseLong(membershipId.getMembershipId()));

    }
}
