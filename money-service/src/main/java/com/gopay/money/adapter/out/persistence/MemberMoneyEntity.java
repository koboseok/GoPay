package com.gopay.money.adapter.out.persistence;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "member_money")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MemberMoneyEntity {

    @Id
    @GeneratedValue
    private Long memberMoneyId;
    private Long membershipId;
    private int balance; // 0: 증액, 1: 감액

    private String aggregateIdentifier;



    public MemberMoneyEntity(Long membershipId, int balance, String aggregateIdentifier) {
        this.membershipId = membershipId;
        this.balance = balance;
        this.aggregateIdentifier = aggregateIdentifier;
    }
}
