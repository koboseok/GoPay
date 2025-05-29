package com.gopay.membership.application.port.in;

import com.gopay.membership.domain.Membership;
import common.UseCase;

@UseCase
public interface RegisterMembershipUseCase {
    // 비즈니스 로직 정의

    Membership registerMembership(RegisterMembershipCommand command);
}
