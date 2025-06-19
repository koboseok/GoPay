package com.gopay.membership.application.port.in;

import com.gopay.common.UseCase;
import com.gopay.membership.domain.Membership;

import java.util.List;


@UseCase
public interface FindMembershipUseCase {
    // 비즈니스 로직 정의

    Membership findMembership(FindMembershipCommand command);

    List<Membership> findMembershipListByAddress(FindMembershipListByAddressCommand command);
}
