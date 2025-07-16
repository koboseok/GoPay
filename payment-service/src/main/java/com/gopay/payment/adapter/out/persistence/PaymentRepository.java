package com.gopay.payment.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    @Query("SELECT e  FROM PaymentEntity e WHERE e.paymentStatus = :paymentStatus")
    List<PaymentEntity> findByPaymentStatus(@Param("paymentStatus") int paymentStatus);
}
