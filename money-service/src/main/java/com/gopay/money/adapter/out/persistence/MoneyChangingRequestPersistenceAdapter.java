package com.gopay.money.adapter.out.persistence;


import com.gopay.common.PersistenceAdapter;
import com.gopay.money.application.port.in.CreateMemberMoneyPort;
import com.gopay.money.application.port.in.GetMemberMoneyPort;
import com.gopay.money.application.port.out.GetMemberMoneyListPort;
import com.gopay.money.application.port.out.IncreaseMoneyPort;
import com.gopay.money.domain.MemberMoney;
import com.gopay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class MoneyChangingRequestPersistenceAdapter implements IncreaseMoneyPort, CreateMemberMoneyPort, GetMemberMoneyPort, GetMemberMoneyListPort {

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


    @Override
    public List<MemberMoneyEntity> getMemberMoneyPort(List<String> membershipIds) {
        // membershipIds 를 기준으로, 여러개의 MemberMoneyJpaEntity 를 가져온다.
        return memberMoneyRepository.fineMemberMoneyListByMembershipIds(convertMembershipIds(membershipIds));
    }

    private List<Long> convertMembershipIds(List<String> membershipIds) {
        List<Long> longList = new ArrayList<>();
        // membershipIds 를 Long 타입의 List 로 변환한다.
        for(String membershipId : membershipIds) {
            longList.add(Long.parseLong(membershipId));
        }
        return longList;
    }
}
