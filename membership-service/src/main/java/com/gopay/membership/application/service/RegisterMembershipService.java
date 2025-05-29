package com.gopay.membership.application.service;

import com.gopay.membership.adapter.out.persistence.MembershipEntity;
import com.gopay.membership.adapter.out.persistence.MembershipMapper;
import com.gopay.membership.application.port.in.RegisterMembershipCommand;
import com.gopay.membership.application.port.in.RegisterMembershipUseCase;
import com.gopay.membership.application.port.out.RegisterMembershipPort;
import com.gopay.membership.domain.Membership;
import common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterMembershipService implements RegisterMembershipUseCase {

    private final RegisterMembershipPort registerMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership registerMembership(RegisterMembershipCommand command) {
        // command -> DB
        // biz logic -> DB
        // external system
        // port, adapter

        MembershipEntity entity = registerMembershipPort.createMembership(
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
