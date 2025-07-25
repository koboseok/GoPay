package com.gopay.membership.adapter.in.web;


import com.gopay.common.WebAdapter;
import com.gopay.membership.application.port.in.FindMembershipCommand;
import com.gopay.membership.application.port.in.FindMembershipListByAddressCommand;
import com.gopay.membership.application.port.in.FindMembershipUseCase;
import com.gopay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindMembershipController {

    private final FindMembershipUseCase findMembershipUseCase;

    @GetMapping(path = "/membership/{membershipId}")
    ResponseEntity<Membership> findMembership(@PathVariable("membershipId") String membershipId) {

        FindMembershipCommand command = FindMembershipCommand.builder()
                .membershipId(membershipId)
                .build();

        return ResponseEntity.ok(findMembershipUseCase.findMembership(command));
    }

    @GetMapping(path = "/membership/address/{addressName}")
    ResponseEntity<List<Membership>> findMembershipListByAddress(@PathVariable("addressName") String addressName) {

        FindMembershipListByAddressCommand command = FindMembershipListByAddressCommand.builder()
                .addressName(addressName)
                .build();

        return ResponseEntity.ok(findMembershipUseCase.findMembershipListByAddress(command));
    }
}
