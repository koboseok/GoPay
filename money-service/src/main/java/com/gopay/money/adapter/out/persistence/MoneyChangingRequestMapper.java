package com.gopay.money.adapter.out.persistence;


import com.gopay.money.domain.MoneyChangingRequest;
import org.springframework.stereotype.Component;

@Component
public class MoneyChangingRequestMapper {

    public MoneyChangingRequest mapToDomainEntity(MoneyChangingRequestEntity entity) {
        return MoneyChangingRequest.generateMoneyChangingRequest(
                new MoneyChangingRequest.MoneyChangingRequestId(entity.getMoneyChangingRequestId() + ""),
                new MoneyChangingRequest.TargetMembershipId(entity.getTargetMembershipId()),
                new MoneyChangingRequest.MoneyChangingType(entity.getMoneyChangingType()),
                new MoneyChangingRequest.ChangingMoneyAmount(entity.getAmount()),
                new MoneyChangingRequest.MoneyChangingResultStatus(entity.getChangingMoneyStatus()),
                new MoneyChangingRequest.Uuid(entity.getUuid())
        );
    }
}
