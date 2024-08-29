package com.flance.assessment.dto.response;

import com.flance.assessment.config.HeaderBase;
import com.flance.assessment.model.Wallet;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class LinkBankAccountResponse {
    private HeaderBase headerBase;
    private String accountNumber;
    private String bank;
    private Long walletId;
}
