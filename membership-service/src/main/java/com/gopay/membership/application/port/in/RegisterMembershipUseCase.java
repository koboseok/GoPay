package com.gopay.membership.application.port.in;

import com.gopay.common.UseCase;
import com.gopay.membership.domain.Membership;


@UseCase
public interface RegisterMembershipUseCase {
    // 비즈니스 로직 정의

    Membership registerMembership(RegisterMembershipCommand command);
}
