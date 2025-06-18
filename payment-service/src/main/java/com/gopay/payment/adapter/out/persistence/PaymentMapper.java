package com.gopay.payment.adapter.out.persistence;

import com.gopay.payment.domain.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public Payment mapToDomainEntity(PaymentEntity paymentEntity) {
        return Payment.generatePayment(
                new Payment.PaymentId(paymentEntity.getPaymentId()),
                new Payment.RequestMembershipId(paymentEntity.getRequestMembershipId()),
                new Payment.RequestPrice(paymentEntity.getRequestPrice()),
                new Payment.FranchiseId(paymentEntity.getFranchiseId()),
                new Payment.FranchiseFeeRate(paymentEntity.getFranchiseFeeRate()),
                new Payment.PaymentStatus(paymentEntity.getPaymentStatus()),
                new Payment.ApprovedAt(paymentEntity.getApprovedAt())
        );
    }
}
