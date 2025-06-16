package com.gopay.remittance.application.port.out;


import com.gopay.remittance.adapter.out.persistence.RemittanceRequestEntity;
import com.gopay.remittance.application.port.in.RequestRemittanceCommand;

public interface RequestRemittancePort {

	RemittanceRequestEntity createRemittanceRequestHistory(RequestRemittanceCommand command);
	boolean saveRemittanceRequestHistory(RemittanceRequestEntity entity);
}
