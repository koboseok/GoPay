package com.gopay.banking.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface FirmbankingRequestRepository extends JpaRepository<FirmbankingRequestEntity, Long> {

    @Query("SELECT e  FROM FirmbankingRequestEntity e WHERE e.aggregateIdentifier = :aggregateIdentifier")
    List<FirmbankingRequestEntity> findByAggregateIdentifier(@Param("aggregateIdentifier") String aggregateIdentifier);
}
