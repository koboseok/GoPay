package com.gopay.money.adapter.out.persistence;


import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyChangingRequestRepository extends JpaRepository<MoneyChangingRequestEntity, Long> {
}
