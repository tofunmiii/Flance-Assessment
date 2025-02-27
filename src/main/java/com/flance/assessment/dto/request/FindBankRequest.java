package com.flance.assessment.dto.request;

import com.flance.assessment.model.Wallet;
import lombok.Data;

@Data
public class FindBankRequest {
    private Long walletId;
}
