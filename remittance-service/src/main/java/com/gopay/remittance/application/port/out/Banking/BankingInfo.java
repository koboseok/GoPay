package com.gopay.remittance.application.port.out.Banking;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankingInfo {


    private String bankName;
    private String bankAccountNumber;
    private boolean isValid;

}
