package com.gopay.remittance.application.port.in;

import com.gopay.common.UseCase;
import com.gopay.remittance.domain.RemittanceRequest;


@UseCase
public interface RequestRemittanceUseCase {

	RemittanceRequest requestRemittance(RequestRemittanceCommand command);
}
