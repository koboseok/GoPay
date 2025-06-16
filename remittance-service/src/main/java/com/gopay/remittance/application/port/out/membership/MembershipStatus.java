package com.gopay.remittance.application.port.out.membership;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipStatus {

    private String membershipId;
    private boolean isValid;
}
