package com.gopay.remittance.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RemittanceRequest {
	// 송금 요청에 대한 상태 클래스

	@Getter private final String remittanceRequestId;
	@Getter private final String remittanceFromMembershipId;
	@Getter private final String toBankName;
	@Getter private final String toBankAccountNumber;
	@Getter private final int remittanceType; // 0: membership(내부 고객), 1: bank (외부 고객)
	@Getter private final int amount;
	@Getter private final String remittanceStatus;



	public static RemittanceRequest generateRemittanceRequest(
			RemittanceRequestId remittanceRequestId, RemittanceFromMembershipId remittanceFromMembershipId,
			ToBankName toBankName, ToBankAccountNumber toBankAccountNumber,
			RemittanceType remittanceType, Amount amount, RemittanceStatus remittanceStatus) {
		return new RemittanceRequest(
				remittanceRequestId.getRemittanceRequestId(),
				remittanceFromMembershipId.getRemittanceFromMembershipId(),
				toBankName.getToBankName(),
				toBankAccountNumber.getToBankAccountNumber(),
				remittanceType.getRemittanceType(),
				amount.getAmount(),
				remittanceStatus.getRemittanceStatus()
		);
	}

	@Value
	public static class RemittanceRequestId {
		public RemittanceRequestId(String value) {
			this.remittanceRequestId = value;
		}
		String remittanceRequestId ;
	}

	@Value
	public static class RemittanceFromMembershipId {
		public RemittanceFromMembershipId(String value) {
			this.remittanceFromMembershipId = value;
		}
		String remittanceFromMembershipId ;
	}

	@Value
	public static class ToBankName {
		public ToBankName(String value) {
			this.toBankName = value;
		}
		String toBankName ;
	}

	@Value
	public static class ToBankAccountNumber {
		public ToBankAccountNumber(String value) {
			this.toBankAccountNumber = value;
		}
		String toBankAccountNumber ;
	}

	@Value
	public static class RemittanceType {
		public RemittanceType(int value) {
			this.remittanceType = value;
		}
		int remittanceType ;
	}

	@Value
	public static class Amount {
		public Amount(int value) {
			this.amount = value;
		}
		int amount ;
	}

	@Value
	public static class RemittanceStatus {
		public RemittanceStatus(String value) {
			this.remittanceStatus = value;
		}
		String remittanceStatus ;
	}
}
