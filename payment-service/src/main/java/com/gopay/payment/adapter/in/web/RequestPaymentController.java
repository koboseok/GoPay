package com.gopay.payment.adapter.in.web;

import com.gopay.common.WebAdapter;
import com.gopay.payment.application.port.in.FinishSettlementCommand;
import com.gopay.payment.application.port.in.RequestPaymentCommand;
import com.gopay.payment.application.port.in.RequestPaymentUseCase;
import com.gopay.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    // 0: 승인, 1: 실패, 2: 정산 완료.
    // 정상 결제건들의 대한 list 조회
    @GetMapping(path = "/payment/normal-status")
    List<Payment> getNormalStatusPayments() {

        return requestPaymentUseCase.getNormalStatusPayments();
    }

    // 각 결제건들의 상태를 변경
    @PostMapping(path = "/payment/finish-settlement")
    void finishSettlement(@RequestBody FinishSettlementRequest request) {
        System.out.println("request.getPaymentId() = " + request.getPaymentId());
        requestPaymentUseCase.finishPayment(
                new FinishSettlementCommand(
                        request.getPaymentId()
                )
        );
    }

}
