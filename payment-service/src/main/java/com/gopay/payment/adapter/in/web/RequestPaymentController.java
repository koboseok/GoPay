package com.gopay.payment.adapter.in.web;

import com.gopay.common.WebAdapter;
import com.gopay.payment.application.port.in.RequestPaymentCommand;
import com.gopay.payment.application.port.in.RequestPaymentUseCase;
import com.gopay.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestPaymentController {

    private final RequestPaymentUseCase requestPaymentUseCase;


    @PostMapping(path = "/payment/request")
    Payment requestPayment(@RequestBody PaymentRequest request) {

        return requestPaymentUseCase.requestPayment(
                new RequestPaymentCommand(
                        request.getRequestMembershipId(),
                        request.getRequestPrice(),
                        request.getFranchiseId(),
                        request.getFranchiseFeeRate()
                )
        );
    }
}
