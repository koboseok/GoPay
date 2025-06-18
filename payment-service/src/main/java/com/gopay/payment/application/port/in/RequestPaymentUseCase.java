package com.gopay.payment.application.port.in;

import com.gopay.payment.domain.Payment;

public interface RequestPaymentUseCase {
    Payment requestPayment(RequestPaymentCommand command);
}
