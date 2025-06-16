package com.gopay.remittance.application.port.out.Banking;


public interface BankingPort {


    BankingInfo getMembershipBankingInfo(String bankName, String bankAccountNumber);

    boolean requestFirmbanking(String bankName, String bankAccountNumber, int amount);

}
