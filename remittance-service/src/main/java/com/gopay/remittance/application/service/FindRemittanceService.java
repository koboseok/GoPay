package com.gopay.remittance.application.service;

import com.gopay.common.UseCase;
import com.gopay.remittance.adapter.out.persistence.RemittanceRequestEntity;
import com.gopay.remittance.adapter.out.persistence.RemittanceRequestMapper;
import com.gopay.remittance.application.port.in.FindRemittanceCommand;
import com.gopay.remittance.application.port.in.FindRemittanceUseCase;
import com.gopay.remittance.application.port.out.FindRemittancePort;
import com.gopay.remittance.domain.RemittanceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@UseCase
@Transactional
public class FindRemittanceService implements FindRemittanceUseCase {


    private final FindRemittancePort findRemittancePort;
    private final RemittanceRequestMapper mapper;


    @Override
    public List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command) {
        List<RemittanceRequestEntity> remittance = findRemittancePort.findRemittance(command);
        return null;
    }
}
