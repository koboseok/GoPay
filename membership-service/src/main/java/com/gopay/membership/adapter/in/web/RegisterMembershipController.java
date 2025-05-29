package com.gopay.membership.adapter.in.web;


import com.gopay.membership.application.port.in.RegisterMembershipCommand;
import com.gopay.membership.application.port.in.RegisterMembershipUseCase;
import com.gopay.membership.domain.Membership;
import common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterMembershipController {


    private final RegisterMembershipUseCase registerMembershipUseCase;

    @PostMapping(path = "/membership/register")
    Membership registerMemBership(@RequestBody RegisterMembershipRequest request) {
        // request ~~
        // request -> command
        // UseCase ~~ (request x, command o)
        RegisterMembershipCommand command = RegisterMembershipCommand.builder()
                .name(request.getName())
                .email(request.getEmail())
                .address(request.getAddress())
                .isValid(true)
                .isCorp(request.isCorp())
                .build();


        return registerMembershipUseCase.registerMembership(command);
    }

}
