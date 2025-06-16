package com.gopay.remittance.application.port.out.money;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyInfo {

    // 송금 서비스에서 필요한 머니 정보

    private String membershipId;
    private int balance;
}
