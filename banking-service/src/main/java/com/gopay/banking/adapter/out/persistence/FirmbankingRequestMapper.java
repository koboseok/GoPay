package com.gopay.banking.adapter.out.persistence;

import com.gopay.banking.domain.FirmbankingRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FirmbankingRequestMapper {

    public FirmbankingRequest mapToDomainEntity(FirmbankingRequestEntity entity, UUID uuid) {
        return FirmbankingRequest.generateFirmbankingRequest(
                new FirmbankingRequest.FirmbankingRequestId(entity.getRequestFirmbankingId() + ""),
                new FirmbankingRequest.FromBankName(entity.getFromBankName()),
                new FirmbankingRequest.FromBankAccountNumber(entity.getFromBankAccountNumber()),
                new FirmbankingRequest.ToBankName(entity.getToBankName()),
                new FirmbankingRequest.ToBankAccountNumber(entity.getToBankAccountNumber()),
                new FirmbankingRequest.MoneyAmount(entity.getMoneyAmount()),
                new FirmbankingRequest.FirmbankingStatus(entity.getFirmbankingStatus()),
                uuid,
                new FirmbankingRequest.FirmbankingAggregateIdentifier(entity.getAggregateIdentifier())
        );
    }
}
