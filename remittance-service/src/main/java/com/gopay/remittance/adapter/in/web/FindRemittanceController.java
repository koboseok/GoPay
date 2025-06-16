package com.gopay.remittance.adapter.in.web;


import com.gopay.common.WebAdapter;
import com.gopay.remittance.application.port.in.FindRemittanceCommand;
import com.gopay.remittance.application.port.in.FindRemittanceUseCase;
import com.gopay.remittance.domain.RemittanceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindRemittanceController {

	 private final FindRemittanceUseCase findRemittanceUseCase;

	@GetMapping(path = "/remittance/{membershipId}")
	public List<RemittanceRequest> findRemittanceHistory(@PathVariable String membershipId) {
		FindRemittanceCommand command = FindRemittanceCommand.builder()
				.membershipId(membershipId)
				.build();

		return findRemittanceUseCase.findRemittanceHistory(command);
	}


	@GetMapping(path = "/remittance/history")
	ResponseEntity<Object>  findRemittanceHistoryByMemberId(){


		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}

	// (API Aggregation, Banking + Money)
	@GetMapping(path = "/remittance/transferred-money")
	ResponseEntity<Object>  findMoneyTransferringByRemittanceId(){


		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}
}
