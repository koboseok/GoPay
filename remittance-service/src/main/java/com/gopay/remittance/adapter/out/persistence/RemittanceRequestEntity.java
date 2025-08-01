package com.gopay.remittance.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "request_remittance")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RemittanceRequestEntity {
	@Id
	@GeneratedValue
	private Long remittanceRequestId;



	private String fromMembershipId; // for membership

	private String toMembershipId; // to membership

	private String toBankName;

	private String toBankAccountNumber;

	private int remittanceType; // 0: membership(내부 고객), 1: bank (외부 고객)

	private int amount; // 송금요청금액

	private String remittanceStatus;



}
