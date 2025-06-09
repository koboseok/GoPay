package com.gopay.banking.adapter.out.external.bank;

import com.gopay.banking.adapter.out.persistence.RegisteredBankAccountRepository;
import com.gopay.banking.application.port.out.RequestBankAccountInfoPort;
import com.gopay.banking.application.port.out.RequestExternalFirmbankingPort;
import com.gopay.common.ExternalSystemAdapter;
import lombok.RequiredArgsConstructor;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankAccountAdapter implements RequestBankAccountInfoPort, RequestExternalFirmbankingPort {

    private final RegisteredBankAccountRepository registeredBankAccountRepository;

    @Override
    public BankAccount getBankAccountInfo(GetBankAccountRequest request) {
        // 실제로 외부 은행에 http를 통해서
        // 은행 계좌 정보를 가져오고
        // 실제 은행 계좌 -> BankAccount
        return new BankAccount(request.getBankName(), request.getAccountNumber(), true);
    }


    @Override
    public FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request) {
        // 실제로 외부 은행에 http를 통해서
        // 펌뱅킹 요청을 하고

        // 그 결과를
        // 뱅킹 서비스에서 필요한 정보들만 모델링
        // 외부 은행의 실제 결과를 -> FirmbankingResult로 파싱

        return new FirmbankingResult(0);
    }
}
