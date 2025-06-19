package com.gopay.money.adapter.out.persistence;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberMoneyRepository extends JpaRepository<MemberMoneyEntity, Long> {

    @Query("SELECT e FROM MemberMoneyEntity e WHERE e.membershipId = :membershipId")
    List<MemberMoneyEntity> findByMembershipId(@Param("membershipId") Long membershipId);


    @Query("SELECT e  FROM MemberMoneyEntity e WHERE e.membershipId in :membershipIds")
    List<MemberMoneyEntity> fineMemberMoneyListByMembershipIds(@Param("membershipIds") List<Long> membershipIds);
}
