package com.gopay.remittance.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RemittanceRequestRepository extends JpaRepository<RemittanceRequestEntity, Long> {
}
