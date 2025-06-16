package com.gopay.banking.application.port.in;

import com.gopay.banking.domain.FirmbankingRequest;
import com.gopay.common.UseCase;


@UseCase
public interface RequestFirmbankingUseCase {
    // 비즈니스 로직 정의

    FirmbankingRequest registerFirmbanking(RequestFirmbankingCommand command);
    void requestFirmbankingByEvent(RequestFirmbankingCommand command);
}
