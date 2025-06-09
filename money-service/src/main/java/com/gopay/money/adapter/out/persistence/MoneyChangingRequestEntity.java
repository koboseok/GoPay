package com.gopay.money.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "money_changing_request")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MoneyChangingRequestEntity {

    @Id
    @GeneratedValue
    private Long moneyChangingRequestId;
    private String targetMembershipId;
    private int moneyChangingType; // 0: 증액, 1: 감액
    private int amount;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private int changingMoneyStatus; // 0: 요청, 1: 성공, 2: 실패
    private String uuid;

    public MoneyChangingRequestEntity(String targetMembershipId, int moneyChangingType, int amount, int changingMoneyStatus,  String uuid) {
        this.targetMembershipId = targetMembershipId;
        this.moneyChangingType = moneyChangingType;
        this.amount = amount;
        this.changingMoneyStatus = changingMoneyStatus;
        this.uuid = uuid;

    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
