package com.gopay.banking.adapter.out.persistence;

import com.gopay.banking.application.port.out.RequestFirmbankingPort;
import com.gopay.banking.domain.FirmbankingRequest;
import com.gopay.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class FirmbankingRequestPersistenceAdapter implements RequestFirmbankingPort {

    private final FirmbankingRequestRepository firmbankingRequestRepository;

    @Override
    public FirmbankingRequestEntity createFirmbankingRequest(
        FirmbankingRequest.FromBankName fromBankName, FirmbankingRequest.FromBankAccountNumber fromBankAccountNumber,
        FirmbankingRequest.ToBankName toBankName, FirmbankingRequest.ToBankAccountNumber toBankAccountNumber,
         FirmbankingRequest.MoneyAmount moneyAmount, FirmbankingRequest.FirmbankingStatus firmbankingStatus) {



        return firmbankingRequestRepository.save(
                new FirmbankingRequestEntity(
                        fromBankName.getFromBankName(),
                        fromBankAccountNumber.getFromBankAccountNumber(),
                        toBankName.getToBankName(),
                        toBankAccountNumber.getToBankAccountNumber(),
                        moneyAmount.getMoneyAmount(),
                        firmbankingStatus.getFirmbankingStatus(),
                        UUID.randomUUID()
                )
        );

    }


    @Override
    public FirmbankingRequestEntity modifyFirmbankingRequest(FirmbankingRequestEntity entity) {
        return firmbankingRequestRepository.save(entity);
    }
}
