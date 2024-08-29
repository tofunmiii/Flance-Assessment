package com.flance.assessment.dto.request;

import lombok.Data;

@Data
public class LinkBankAccountRequest {
    private long walletId;
    private String accountNumber;
    private String accountName;
    private String bank;
}
