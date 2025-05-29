package com.gopay.membership.application.service;

import com.gopay.common.UseCase;
import com.gopay.membership.adapter.out.persistence.MembershipEntity;
import com.gopay.membership.adapter.out.persistence.MembershipMapper;
import com.gopay.membership.application.port.in.ModifyMembershipCommand;
import com.gopay.membership.application.port.in.ModifyMembershipUseCase;
import com.gopay.membership.application.port.out.ModifyMembershipPort;
import com.gopay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class ModifyMembershipService implements ModifyMembershipUseCase {

    private final ModifyMembershipPort modifyMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership modifyMembership(ModifyMembershipCommand command) {
        // command -> DB
        // biz logic -> DB
        // external system
        // port, adapter
        MembershipEntity entity = modifyMembershipPort.modifyMembership(
                new Membership.MembershipId(command.getMemberShipId()),
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress()),
                new Membership.MembershipIsValid(command.isValid()),
                new Membership.MembershipIsCorp(command.isCorp())
        );

        // entity -> Membership dto

        return membershipMapper.mapToDomainEntity(entity);
    }

}
