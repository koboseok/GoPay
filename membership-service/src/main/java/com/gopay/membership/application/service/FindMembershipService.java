package com.gopay.membership.application.service;

import com.gopay.common.UseCase;
import com.gopay.membership.adapter.out.persistence.MembershipEntity;
import com.gopay.membership.adapter.out.persistence.MembershipMapper;
import com.gopay.membership.application.port.in.FindMembershipCommand;
import com.gopay.membership.application.port.in.FindMembershipListByAddressCommand;
import com.gopay.membership.application.port.in.FindMembershipUseCase;
import com.gopay.membership.application.port.out.FindMembershipPort;
import com.gopay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Membership> findMembershipListByAddress(FindMembershipListByAddressCommand command) {

        List<MembershipEntity> membershipEntityList = findMembershipPort.findMembershipListByAddress(new Membership.MembershipAddress(command.getAddressName()));
        List<Membership> memberships = new ArrayList<>();

        for (MembershipEntity membershipEntity : membershipEntityList) {
            memberships.add(membershipMapper.mapToDomainEntity(membershipEntity));
        }

        return memberships;
    }
}
