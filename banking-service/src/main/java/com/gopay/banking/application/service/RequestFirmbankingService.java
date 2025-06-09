package com.gopay.banking.application.service;

import com.gopay.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.gopay.banking.adapter.out.external.bank.FirmbankingResult;
import com.gopay.banking.adapter.out.persistence.FirmbankingRequestEntity;
import com.gopay.banking.adapter.out.persistence.FirmbankingRequestMapper;
import com.gopay.banking.application.port.in.FirmbankingRequestCommand;
import com.gopay.banking.application.port.in.RequestFirmbankingUseCase;
import com.gopay.banking.application.port.out.RequestExternalFirmbankingPort;
import com.gopay.banking.application.port.out.RequestFirmbankingPort;
import com.gopay.banking.domain.FirmbankingRequest;
import com.gopay.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestFirmbankingService implements RequestFirmbankingUseCase {

    private final RequestFirmbankingPort requestFirmbankingPort;
    private final RequestExternalFirmbankingPort requestExternalFirmbankingPort;
    private final FirmbankingRequestMapper mapper;


    @Override
    public FirmbankingRequest registerFirmbanking(FirmbankingRequestCommand command) {


        // business logic
        // a -> b 계좌
        // 1. 요청에 대해 정보를 먼저 write "요청" 상태로
        FirmbankingRequestEntity firmbankingRequestEntity = requestFirmbankingPort.createFirmbankingRequest(
                new FirmbankingRequest.FromBankName(command.getFromBankName()),
                new FirmbankingRequest.FromBankAccountNumber(command.getToBankAccountNumber()),
                new FirmbankingRequest.ToBankName(command.getToBankName()),
                new FirmbankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
                new FirmbankingRequest.FirmbankingStatus(0)
        );


        // 2. 외부 은행에 펌뱅킹 요청
        FirmbankingResult firmbankingResult = requestExternalFirmbankingPort.requestExternalFirmbanking(new ExternalFirmbankingRequest(
                command.getFromBankName(),
                command.getFromBankAccountNumber(),
                command.getToBankName(),
                command.getToBankAccountNumber()
        ));
        // transactional UUID
        UUID randomUUID = UUID.randomUUID();
        firmbankingRequestEntity.setUuid(randomUUID.toString());
        // 3. 결과에 따라서 1번에서 작성했던 FirmbankingRequest 정보를 update
        if (firmbankingResult.getResultCode() == 0) {
            // 성공

            firmbankingRequestEntity.setFirmbankingStatus(1);
        } else {
            // 실패
            firmbankingRequestEntity.setFirmbankingStatus(2);
        }

        // 4. 바뀐 상태 값을 기준으로 다시 save 후 리턴
        return mapper.mapToDomainEntity(requestFirmbankingPort.modifyFirmbankingRequest(firmbankingRequestEntity), randomUUID);
    }
}
