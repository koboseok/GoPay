package com.gopay.payment.adapter.out.persistence;

import com.gopay.common.PersistenceAdapter;
import com.gopay.payment.application.port.out.CreatePaymentPort;
import com.gopay.payment.domain.Payment;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements CreatePaymentPort {


    private final PaymentRepository paymentRepository;
    private final PaymentMapper mapper;


    @Override
    public Payment createPayment(String requestMembershipId, String requestPrice, String franchiseId, String franchiseFeeRate) {
        PaymentEntity jpaEntity = paymentRepository.save(
                new PaymentEntity(
                        requestMembershipId,
                        Integer.parseInt(requestPrice),
                        franchiseId,
                        franchiseFeeRate,
                        0, // 0: 승인, 1: 실패, 2: 정산 완료.
                        null
                )
        );
        return mapper.mapToDomainEntity(jpaEntity);
    }


    @Override
    public List<Payment> getNormalStatusPayments() {
        List<Payment> payments = new ArrayList<>();
        List<PaymentEntity> paymentEntities = paymentRepository.findByPaymentStatus(0);
        if (paymentEntities != null) {
            for (PaymentEntity paymentEntity : paymentEntities) {
                payments.add(mapper.mapToDomainEntity(paymentEntity));
            }
            return payments;
        }
        return null;
    }

    @Override
    public void changePaymentRequestStatus(String paymentId, int status) {
        Optional<PaymentEntity> paymentJpaEntity = paymentRepository.findById(Long.parseLong(paymentId));
        if (paymentJpaEntity.isPresent()) {
            paymentJpaEntity.get().setPaymentStatus(status);
            paymentRepository.save(paymentJpaEntity.get());
        }
    }

}
