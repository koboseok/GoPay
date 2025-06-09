package com.gopay.money.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
public class MemberMoneyEntity {

    @Id
    @GeneratedValue
    private Long memberMoneyId;
    private Long memberShipId;
    private int balance; // 0: 증액, 1: 감액



    public MemberMoneyEntity(Long memberShipId, int balance) {
        this.memberShipId = memberShipId;
        this.balance = balance;
    }
}
