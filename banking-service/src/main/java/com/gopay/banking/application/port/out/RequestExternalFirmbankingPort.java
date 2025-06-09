package com.gopay.banking.application.port.out;

import com.gopay.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.gopay.banking.adapter.out.external.bank.FirmbankingResult;

public interface RequestExternalFirmbankingPort {

    // 외부 은행에 펌뱅킹 요청을 위한 인터페이스
    FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest externalFirmbankingRequest);



}
