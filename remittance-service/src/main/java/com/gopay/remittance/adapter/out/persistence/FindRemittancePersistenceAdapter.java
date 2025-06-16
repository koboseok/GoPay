package com.gopay.remittance.adapter.out.persistence;


import com.gopay.common.PersistenceAdapter;
import com.gopay.remittance.application.port.in.FindRemittanceCommand;
import com.gopay.remittance.application.port.out.FindRemittancePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@PersistenceAdapter
public class FindRemittancePersistenceAdapter implements FindRemittancePort {

    private final RemittanceRequestRepository remittanceMoneyRepository;

    @Override
    public List<RemittanceRequestEntity> findRemittance(FindRemittanceCommand command) {

        // use jpa
        return null;
    }
}
