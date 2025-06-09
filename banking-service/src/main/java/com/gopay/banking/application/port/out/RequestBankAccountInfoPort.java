package com.gopay.banking.application.port.out;

import com.gopay.banking.adapter.out.external.bank.BankAccount;
import com.gopay.banking.adapter.out.external.bank.GetBankAccountRequest;

public interface RequestBankAccountInfoPort {
   // 외부 은행과의 통신을 위한 인터페이스
   BankAccount getBankAccountInfo(GetBankAccountRequest request);
}
