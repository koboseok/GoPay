package com.gopay.membership.application.port.in;


import com.gopay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class FindMembershipCommand extends SelfValidating<FindMembershipCommand> {

    @NotNull
    private final String membershipId;




    public FindMembershipCommand(String membershipId) {
       this.membershipId = membershipId;

        this.validateSelf();
    }
}
