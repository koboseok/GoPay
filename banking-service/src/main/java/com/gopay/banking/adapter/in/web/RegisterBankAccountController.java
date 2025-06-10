package com.gopay.banking.adapter.in.web;


import com.gopay.banking.application.port.in.RegisterBankAccountCommand;
import com.gopay.banking.application.port.in.RegisterBankAccountUseCase;
import com.gopay.banking.domain.RegisteredBankAccount;
import com.gopay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterBankAccountController {


    private final RegisterBankAccountUseCase registerBankAccountUseCase;

    // TODO: select

    @PostMapping(path = "/banking/account/register")
    RegisteredBankAccount registeredBankAccount(@RequestBody RegisterBankAccountRequest request) {
        // request ~~
        // request -> command
        // UseCase ~~ (request x, command o)
        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
                .membershipId(request.getMembershipId())
                .bankName(request.getBankName())
                .bankAccountNumber(request.getBankAccountNumber())
                .linkedStatusIsValid(request.isLinkedStatusIsValid())
                .build();


        RegisteredBankAccount registeredBankAccount = registerBankAccountUseCase.registerBankAccount(command);

        if (registeredBankAccount == null) {
            // TODO: Error Handling
            return null;
        }

        return registeredBankAccount;
    }

}
