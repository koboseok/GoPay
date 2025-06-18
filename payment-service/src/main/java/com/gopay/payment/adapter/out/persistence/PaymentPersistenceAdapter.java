package com.gopay.payment.adapter.out.persistence;

import com.gopay.payment.application.port.out.CreatePaymentPort;
import com.gopay.payment.domain.Payment;
import com.gopay.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

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
}
