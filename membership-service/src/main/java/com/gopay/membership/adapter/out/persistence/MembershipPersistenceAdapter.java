package com.gopay.membership.adapter.out.persistence;

import com.gopay.common.PersistenceAdapter;
import com.gopay.membership.application.port.out.FindMembershipPort;
import com.gopay.membership.application.port.out.ModifyMembershipPort;
import com.gopay.membership.application.port.out.RegisterMembershipPort;
import com.gopay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort, ModifyMembershipPort {

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
        return membershipRepository.getReferenceById(Long.parseLong(membershipId.getMembershipId()));

    }


    @Override
    public MembershipEntity modifyMembership(Membership.MembershipId membershipId, Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp) {

        MembershipEntity entity = membershipRepository.getReferenceById(Long.parseLong(membershipId.getMembershipId()));

        entity.setName(membershipName.getNameValue());
        entity.setEmail(membershipEmail.getEmailValue());
        entity.setAddress(membershipAddress.getAddressValue());
        entity.setValid(membershipIsValid.isValidValue());
        entity.setCorp(membershipIsCorp.isCorp());

        return  membershipRepository.save(entity);
    }
}
