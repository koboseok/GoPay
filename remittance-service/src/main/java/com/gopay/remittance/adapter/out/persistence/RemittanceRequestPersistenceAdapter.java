package com.gopay.remittance.adapter.out.persistence;


import com.gopay.common.PersistenceAdapter;
import com.gopay.remittance.application.port.in.RequestRemittanceCommand;
import com.gopay.remittance.application.port.out.RequestRemittancePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class RemittanceRequestPersistenceAdapter implements RequestRemittancePort {

    private final RemittanceRequestRepository remittanceMoneyRepository;

    @Override
    public RemittanceRequestEntity createRemittanceRequestHistory(RequestRemittanceCommand command) {


        return remittanceMoneyRepository.save(RemittanceRequestEntity.builder()
                .fromMembershipId(command.getFromMembershipId())
                .toMembershipId(command.getToMembershipId())
                .toBankName(command.getToBankName())
                .toBankAccountNumber(command.getToBankAccountNumber())
                .amount(command.getAmount())
                .remittanceType(command.getRemittanceType())
                .build());

    }

    @Override
    public boolean saveRemittanceRequestHistory(RemittanceRequestEntity entity) {
        remittanceMoneyRepository.save(entity);
        return true;
    }
}
