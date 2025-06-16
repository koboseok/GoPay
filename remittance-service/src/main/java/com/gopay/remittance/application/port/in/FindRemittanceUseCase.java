package com.gopay.remittance.application.port.in;

import com.gopay.common.UseCase;
import com.gopay.remittance.domain.RemittanceRequest;

import java.util.List;


@UseCase
public interface FindRemittanceUseCase {

	List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command);
}
