package com.flance.assessment.dto.response;

import com.flance.assessment.config.HeaderBase;
import com.flance.assessment.model.BankAccount;
import com.flance.assessment.model.Wallet;
import lombok.Data;

import java.util.List;

@Data
public class FindBankResponse {
    private HeaderBase headerBase;
    private String bank;
    private String accountName;
    private String accountNumber;
//   private List<BankAccount> banks;
}
