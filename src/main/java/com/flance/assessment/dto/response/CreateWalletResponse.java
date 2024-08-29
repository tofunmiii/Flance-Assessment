package com.flance.assessment.dto.response;

import com.flance.assessment.config.HeaderBase;
import lombok.Data;

@Data
public class CreateWalletResponse {
    private HeaderBase headerBase;
    private String email;
    private Long walletId;
}
