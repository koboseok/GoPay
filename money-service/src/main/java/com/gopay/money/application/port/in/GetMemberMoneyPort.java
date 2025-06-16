package com.gopay.money.application.port.in;

import com.gopay.money.adapter.out.persistence.MemberMoneyEntity;
import com.gopay.money.domain.MemberMoney;

public interface GetMemberMoneyPort {
    MemberMoneyEntity getMemberMoney(
            MemberMoney.MembershipId memberId
    );
}
