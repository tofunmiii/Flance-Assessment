package com.flance.assessment.dto.response;

import com.flance.assessment.config.HeaderBase;
import com.flance.assessment.model.Wallet;
import lombok.Data;

@Data
public class FindWalletResponse {
    private HeaderBase headerBase;
    private Long walletId;
    private String email;
    private String phoneNumber;
}
