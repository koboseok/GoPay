package com.gopay.remittance.application.service;


import com.gopay.common.UseCase;
import com.gopay.remittance.adapter.out.persistence.RemittanceRequestEntity;
import com.gopay.remittance.adapter.out.persistence.RemittanceRequestMapper;
import com.gopay.remittance.application.port.in.RequestRemittanceCommand;
import com.gopay.remittance.application.port.in.RequestRemittanceUseCase;
import com.gopay.remittance.application.port.out.Banking.BankingPort;
import com.gopay.remittance.application.port.out.RequestRemittancePort;
import com.gopay.remittance.application.port.out.membership.MemBershipPort;
import com.gopay.remittance.application.port.out.membership.MembershipStatus;
import com.gopay.remittance.application.port.out.money.MoneyInfo;
import com.gopay.remittance.application.port.out.money.MoneyPort;
import com.gopay.remittance.domain.RemittanceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class RequestRemittanceService implements RequestRemittanceUseCase {

	private final RequestRemittancePort requestRemittancePort;
	private final RemittanceRequestMapper mapper;
	private final MemBershipPort memBershipPort;
	private final MoneyPort moneyPort;
	private final BankingPort bankingPort;

	@Override
	public RemittanceRequest requestRemittance(RequestRemittanceCommand command) {

		// 0. 송금 요청 상태를 시작 상태로 기록 (persistence layer)
		RemittanceRequestEntity entity = requestRemittancePort.createRemittanceRequestHistory(command);

		// 1. from 멤버십 상태 확인 ( membership service)
		MembershipStatus membershipStatus = memBershipPort.getMembershipStatus(command.getFromMembershipId());
		if (!membershipStatus.isValid()) {
			return null;
		}
		// 2. 잔액 존재하는지 확인 (money service)
		MoneyInfo moneyInfo = moneyPort.getMoneyInfo(command.getFromMembershipId());

		// 잔액이 충분하지 않은 경우 즉, 충전이 필요한 경우
		if (moneyInfo.getBalance() < command.getAmount()) {
			// command.getAmount() - moneyInfo.getBalance() 해당 금액보다 큰 금액의 1만원 단위로 세팅
			// 만원 단위로 올림하는 Math 함수 사용
			int rechargeAmount = (int) Math.ceil((moneyInfo.getBalance() - command.getAmount()) / 10000.0) * 10000;

			// 2-1. 잔액이 충분하지 않다면 충전요청 (money service)
			boolean moneyResult = moneyPort.requestMoneyRecharging(command.getFromMembershipId(), rechargeAmount);
			if(!moneyResult) {
				return null;
			}
		}

		// 3. 송금 타입 (고객/은행)
		if (command.getRemittanceType() == 0) {
			// 3-1. 내부 고객일 경우
			boolean fromRemittanceResult = false;
			boolean toRemittanceResult = false;

			// from 고객 머니 감액, to 고객 머니 증액 (money service)
			fromRemittanceResult = moneyPort.requestMoneyDecrease(command.getFromMembershipId(), command.getAmount());
			toRemittanceResult = moneyPort.requestMoneyIncrease(command.getToMembershipId(), command.getAmount());


			// 둘 중 하나라도 실패한 경우
			if (!fromRemittanceResult || !toRemittanceResult) {
				return null;
			}


		} else if (command.getRemittanceType() == 1) {
			// 3-2. 외부 은행 계좌
			// 외부 은행 계좌가 적절한지 확인 (banking service)
			// 법인 계좌 -> 외부 은행 계좌 펌뱅킹 요청 (banking service)
			boolean remittanceResult = bankingPort.requestFirmbanking(command.getToBankName(), command.getToBankAccountNumber(), command.getAmount());
			if (!remittanceResult) {
				return null;
			}


		}

		// 4. 송금 요청 상태를 성공으로 기록 (persistence layer)
		//  송금 요청 기록  // 송금이 완료된 기록
		entity.setRemittanceStatus("success");
		boolean result = requestRemittancePort.saveRemittanceRequestHistory(entity);
		if (result) {
			return mapper.mapToDomainEntity(entity);
		}


		return null;
	}
}




