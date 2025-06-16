package com.gopay.banking.application.port.out;

import com.gopay.banking.adapter.out.persistence.FirmbankingRequestEntity;
import com.gopay.banking.domain.FirmbankingRequest;


public interface RequestFirmbankingPort {

    FirmbankingRequestEntity createFirmbankingRequest(
            FirmbankingRequest.FromBankName fromBankName,
            FirmbankingRequest.FromBankAccountNumber fromBankAccountNumber,
            FirmbankingRequest.ToBankName toBankName,
            FirmbankingRequest.ToBankAccountNumber toBankAccountNumber,
            FirmbankingRequest.MoneyAmount moneyAmount,
            FirmbankingRequest.FirmbankingStatus firmbankingStatus,
            FirmbankingRequest.FirmbankingAggregateIdentifier firmbankingAggregateIdentifier



    );



    FirmbankingRequestEntity modifyFirmbankingRequest(
            FirmbankingRequestEntity entity
    );


    FirmbankingRequestEntity getFirmbankingRequest(
            FirmbankingRequest.FirmbankingAggregateIdentifier firmbankingAggregateIdentifier
    );
}
