package com.gopay.money.adapter.out.persistence;


import com.gopay.common.PersistenceAdapter;
import com.gopay.money.application.port.out.IncreaseMoneyPort;
import com.gopay.money.domain.MemberMoney;
import com.gopay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class MoneyChangingRequestPersistenceAdapter implements IncreaseMoneyPort {

    private final MoneyChangingRequestRepository moneyChangingRequestRepository;
    private final MemberMoneyRepository memberMoneyRepository;

    @Override
    public MoneyChangingRequestEntity createMoneyChangingRequest(MoneyChangingRequest.TargetMembershipId targetMembershipId, MoneyChangingRequest.MoneyChangingType moneyChangingType, MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount, MoneyChangingRequest.MoneyChangingResultStatus moneyChangingResultStatus, MoneyChangingRequest.Uuid uuid) {
        return moneyChangingRequestRepository.save(
                new MoneyChangingRequestEntity(
                        targetMembershipId.getTargetMembershipId(),
                        moneyChangingType.getMoneyChangingType(),
                        changingMoneyAmount.getChangingMoneyAmount(),
                        moneyChangingResultStatus.getChangingMoneyStatus(),
                        uuid.getUuid()
                )
        );

    }


    @Override
    public MemberMoneyEntity increaseMoney(MemberMoney.MembershipId membershipId, int increaseMoneyAccount) {

        MemberMoneyEntity memberMoneyEntity;
        try{
            List<MemberMoneyEntity> entityList = memberMoneyRepository.findByMemberShipId(Long.parseLong(membershipId.getMembershipId()));
            memberMoneyEntity = entityList.get(0);

            memberMoneyEntity.setBalance(memberMoneyEntity.getBalance() + increaseMoneyAccount);

            return memberMoneyRepository.save(memberMoneyEntity);

        } catch (Exception e) {
            memberMoneyEntity = new MemberMoneyEntity(
                    Long.parseLong(membershipId.getMembershipId()),
                    increaseMoneyAccount
            );

            return memberMoneyRepository.save(memberMoneyEntity);

        }
    }
}
