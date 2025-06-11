package com.gopay.money.adapter.in.web;


import com.gopay.common.WebAdapter;
import com.gopay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.gopay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.gopay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestMoneyChangingController {


    private final IncreaseMoneyRequestUseCase increaseMoneyRequestUseCase;
    private final MoneyChangingResultDetailMapper mapper;
//    private final DecreaseMoneyRequestUseCase decreaseMoneyRequestUseCase;

    // TODO: select

    @PostMapping(path = "/money/increase")
    MoneyChangingResultDetail increaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {
        // request ~~
        // request -> command
        // UseCase ~~ (request x, command o)
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();
        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequest(command);

        //  MoneyChangingRequest -> MoneyChangingResultDetail
        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                0,
                0,
                moneyChangingRequest.getChangingMoneyAmount()

        );

        return resultDetail;

    }

    @PostMapping(path = "/money/increase-async")
    MoneyChangingResultDetail increaseMoneyChangingRequestAsync(@RequestBody IncreaseMoneyChangingRequest request) {
        // request ~~
        // request -> command
        // UseCase ~~ (request x, command o)
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();
        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequestAsync(command);

        //  MoneyChangingRequest -> MoneyChangingResultDetail
        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                0,
                0,
                moneyChangingRequest.getChangingMoneyAmount()

        );

        return resultDetail;

    }


    @PostMapping(path = "/money/decrease")
    MoneyChangingResultDetail decreaseMoneyChangingRequest(@RequestBody DecreaseMoneyChangingRequest request) {
        // request ~~
        // request -> command
        // UseCase ~~ (request x, command o)


        //
        // return decreaseMoneyRequestUseCase.decreaseMoneyChangingRequest(command);
        return null;
    }

}
