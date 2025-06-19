package com.gopay.membership.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MembershipRepository extends JpaRepository<MembershipEntity, Long> {

    @Query("SELECT e  FROM MembershipEntity e WHERE e.address = :address")
    List<MembershipEntity> findByAddress(@Param("address") String address);

}
