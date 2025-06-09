package com.gopay.banking.adapter.out.external.bank;


import lombok.Data;

@Data
public class GetBankAccountRequest {

    private String bankName;
    private String accountNumber;

    public GetBankAccountRequest(String bankName, String accountNumber) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
    }
}
