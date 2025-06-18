package com.gopay.banking.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegisteredBankAccountRepository extends JpaRepository<RegisteredBankAccountEntity, Long> {
    @Query("SELECT e  FROM RegisteredBankAccountEntity e WHERE e.membershipId = :membershipId")
    List<RegisteredBankAccountEntity> findByMembershipId(@Param("membershipId") String membershipId);
}
