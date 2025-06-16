package com.gopay.money.adapter.out.persistence;


import com.gopay.common.PersistenceAdapter;
import com.gopay.money.application.port.in.CreateMemberMoneyPort;
import com.gopay.money.application.port.in.GetMemberMoneyPort;
import com.gopay.money.application.port.out.IncreaseMoneyPort;
import com.gopay.money.domain.MemberMoney;
import com.gopay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class MoneyChangingRequestPersistenceAdapter implements IncreaseMoneyPort, CreateMemberMoneyPort, GetMemberMoneyPort {

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
            List<MemberMoneyEntity> entityList = memberMoneyRepository.findByMembershipId(Long.parseLong(membershipId.getMembershipId()));
            memberMoneyEntity = entityList.get(0);

            memberMoneyEntity.setBalance(memberMoneyEntity.getBalance() + increaseMoneyAccount);

            return memberMoneyRepository.save(memberMoneyEntity);

        } catch (Exception e) {
            memberMoneyEntity = new MemberMoneyEntity(
                    Long.parseLong(membershipId.getMembershipId()),
                    0,""
            );

            return memberMoneyRepository.save(memberMoneyEntity);

        }
    }


    @Override
    public void createMemberMoney(MemberMoney.MembershipId memberId, MemberMoney.MoneyAggregateIdentifier aggregateIdentifier) {
        // TODO: membershipId check ipc membership-service

        MemberMoneyEntity entity = MemberMoneyEntity.builder()
                .membershipId(Long.parseLong(memberId.getMembershipId()))
                .balance(0)
                .aggregateIdentifier(aggregateIdentifier.getAggregateIdentifier())
                .build();

        memberMoneyRepository.save(entity);
    }


    @Override
    public MemberMoneyEntity getMemberMoney(MemberMoney.MembershipId memberId) {
        MemberMoneyEntity entity;
        List<MemberMoneyEntity> entityList =  memberMoneyRepository.findByMembershipId(Long.parseLong(memberId.getMembershipId()));
        if(entityList.isEmpty()){
            entity = new MemberMoneyEntity(
                    Long.parseLong(memberId.getMembershipId()),
                    0, ""
            );
            entity = memberMoneyRepository.save(entity);
            return entity;
        }
        return  entityList.get(0);
    }

}
