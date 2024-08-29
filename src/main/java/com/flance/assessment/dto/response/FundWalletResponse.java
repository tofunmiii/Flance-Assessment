package com.flance.assessment.dto.response;

import com.flance.assessment.config.HeaderBase;
import com.flance.assessment.model.Transaction;
import lombok.Data;

@Data
public class FundWalletResponse {
    private HeaderBase headerBase;
    private Transaction transaction;
}
