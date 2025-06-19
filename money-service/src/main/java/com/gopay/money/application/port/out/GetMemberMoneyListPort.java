package com.gopay.money.application.port.out;

import com.gopay.money.adapter.out.persistence.MemberMoneyEntity;

import java.util.List;

public interface GetMemberMoneyListPort {
    List<MemberMoneyEntity> getMemberMoneyPort(List<String> membershipIds);
}
