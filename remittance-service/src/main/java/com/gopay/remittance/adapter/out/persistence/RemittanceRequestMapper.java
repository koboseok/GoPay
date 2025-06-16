package com.gopay.remittance.adapter.out.persistence;

import com.gopay.remittance.domain.RemittanceRequest;
import org.springframework.stereotype.Component;

@Component
public class RemittanceRequestMapper {

	public RemittanceRequest mapToDomainEntity(
			RemittanceRequestEntity entity) {

		return RemittanceRequest.generateRemittanceRequest(
				new RemittanceRequest.RemittanceRequestId(entity.getRemittanceRequestId()+ ""),
				new RemittanceRequest.RemittanceFromMembershipId(entity.getFromMembershipId()),
				new RemittanceRequest.ToBankName(entity.getToBankName()),
				new RemittanceRequest.ToBankAccountNumber(entity.getToBankAccountNumber()),
				new RemittanceRequest.RemittanceType(entity.getRemittanceType()),
				new RemittanceRequest.Amount(entity.getAmount()),
				new RemittanceRequest.RemittanceStatus(entity.getRemittanceStatus())
		);
	}
}
