package com.gopay.membership.application.service;

import com.gopay.membership.adapter.out.persistence.MembershipEntity;
import com.gopay.membership.adapter.out.persistence.MembershipMapper;
import com.gopay.membership.application.port.in.FindMembershipCommand;
import com.gopay.membership.application.port.in.FindMembershipUseCase;
import com.gopay.membership.application.port.out.FindMembershipPort;
import com.gopay.membership.domain.Membership;
import common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindMembershipService implements FindMembershipUseCase {

    private final FindMembershipPort findMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership findMembership(FindMembershipCommand command) {

        MembershipEntity membership = findMembershipPort.findMembership(new Membership.MembershipId(command.getMembershipId()));

        return membershipMapper.mapToDomainEntity(membership);
    }


}
