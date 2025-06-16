package com.gopay.remittance.application.port.in;


import com.gopay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class FindRemittanceCommand extends SelfValidating<FindRemittanceCommand> {

    @NotNull
    private String membershipId; // for membership


    public FindRemittanceCommand(String membershipId) {
        this.membershipId = membershipId;

        this.validateSelf();
    }
}
