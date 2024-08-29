package com.flance.assessment.dto.request;

import com.flance.assessment.config.HeaderBase;
import lombok.Data;

@Data
public class CreateWalletRequest {
//    private HeaderBase headerBase;
    private String email;
    private String phoneNumber;
}
