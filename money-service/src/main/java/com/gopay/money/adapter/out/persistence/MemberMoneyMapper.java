package com.gopay.money.adapter.out.persistence;

import com.gopay.money.domain.MemberMoney;
import org.springframework.stereotype.Component;

@Component
public class MemberMoneyMapper {
    public MemberMoney mapToDomainEntity(MemberMoneyEntity memberMoneyJpaEntity) {
        return MemberMoney.generateMemberMoney(
                new MemberMoney.MemberMoneyId(memberMoneyJpaEntity.getMemberMoneyId()+""),
                new MemberMoney.MembershipId(memberMoneyJpaEntity.getMembershipId()+""),
                new MemberMoney.Balance(memberMoneyJpaEntity.getBalance())
        );
    }
}