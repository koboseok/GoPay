package com.gopay.remittance.application.port.out;


import com.gopay.remittance.adapter.out.persistence.RemittanceRequestEntity;
import com.gopay.remittance.application.port.in.FindRemittanceCommand;

import java.util.List;

public interface FindRemittancePort {

	List<RemittanceRequestEntity> findRemittance(FindRemittanceCommand command);

}
