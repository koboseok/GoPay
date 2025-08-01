package com.gopay.banking.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "request_firmbanking")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FirmbankingRequestEntity {

    @Id
    @GeneratedValue
    private Long requestFirmbankingId;
    private String fromBankName;
    private String fromBankAccountNumber;
    private String toBankName;
    private String toBankAccountNumber;
    private int moneyAmount;
    private int firmbankingStatus;
    private String uuid;

    private String aggregateIdentifier;

    public FirmbankingRequestEntity(String fromBankName, String fromBankAccountNumber, String toBankName, String toBankAccountNumber, int moneyAmount, int firmbankingStatus, UUID uuid, String aggregateIdentifier) {
        this.fromBankName = fromBankName;
        this.fromBankAccountNumber = fromBankAccountNumber;
        this.toBankName = toBankName;
        this.toBankAccountNumber = toBankAccountNumber;
        this.moneyAmount = moneyAmount;
        this.firmbankingStatus = firmbankingStatus;
        this.uuid = uuid.toString();
        this.aggregateIdentifier = aggregateIdentifier;
    }
}
