package com.gopay.banking.adapter.in.web;


import com.gopay.banking.application.port.in.FirmbankingRequestCommand;
import com.gopay.banking.application.port.in.RequestFirmbankingUseCase;
import com.gopay.banking.domain.FirmbankingRequest;
import com.gopay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmbankingController {


    private final RequestFirmbankingUseCase requestFirmbankingUseCase;

    // TODO: select

    @PostMapping(path = "/banking/firmbanking/register")
    FirmbankingRequest registerFirmBanking(@RequestBody RequestFirmbankingRequest request) {


        FirmbankingRequestCommand command = FirmbankingRequestCommand.builder()
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .toBankName(request.getToBankName())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
                .build();

        return requestFirmbankingUseCase.registerFirmbanking(command);
    }

}
