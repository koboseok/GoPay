package com.gopay.money.application.port.out;


import com.gopay.money.adapter.out.persistence.MemberMoneyEntity;
import com.gopay.money.adapter.out.persistence.MoneyChangingRequestEntity;
import com.gopay.money.domain.MemberMoney;
import com.gopay.money.domain.MoneyChangingRequest;

public interface IncreaseMoneyPort {

    MoneyChangingRequestEntity createMoneyChangingRequest(
            MoneyChangingRequest.TargetMembershipId targetMembershipId,
            MoneyChangingRequest.MoneyChangingType moneyChangingType,
            MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount,
            MoneyChangingRequest.MoneyChangingResultStatus moneyChangingResultStatus,
            MoneyChangingRequest.Uuid uuid

    );


    MemberMoneyEntity increaseMoney(
            MemberMoney.MembershipId membershipId,
            int increaseMoneyAccount

    );

}
