package com.gopay.banking.adapter.in.web;


import com.gopay.banking.application.port.in.RequestFirmbankingCommand;
import com.gopay.banking.application.port.in.RequestFirmbankingUseCase;
import com.gopay.banking.application.port.in.UpdateFirmbankingCommand;
import com.gopay.banking.application.port.in.UpdateFirmbankingUseCase;
import com.gopay.banking.domain.FirmbankingRequest;
import com.gopay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmbankingController {


    private final RequestFirmbankingUseCase requestFirmbankingUseCase;
    private final UpdateFirmbankingUseCase updateFirmbankingUseCase;
    // TODO: select

    @PostMapping(path = "/banking/firmbanking/register")
    FirmbankingRequest registerFirmBanking(@RequestBody RequestFirmbankingRequest request) {


        RequestFirmbankingCommand command = RequestFirmbankingCommand.builder()
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .toBankName(request.getToBankName())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
                .build();

        return requestFirmbankingUseCase.registerFirmbanking(command);
    }

    @PostMapping(path = "/banking/firmbanking/request-eda")
    void requestFirmbankingByEvent(@RequestBody RequestFirmbankingRequest request) {
        RequestFirmbankingCommand command = RequestFirmbankingCommand.builder()
                .toBankName(request.getToBankName())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
                .build();

        requestFirmbankingUseCase.requestFirmbankingByEvent(command);
    }

    @PutMapping(path = "/banking/firmbanking/update-eda")
    void updateFirmbankingByEvent(@RequestBody UpdateFirmbankingRequest request) {
        UpdateFirmbankingCommand command = UpdateFirmbankingCommand.builder()
                .firmbankingAggregateIdentifier(request.getFirmbankingRequestAggregateIdentifier())
                .firmbankingStatus(request.getStatus())
                .build();

        updateFirmbankingUseCase.updateFirmbankingByEvent(command);
    }

}
