package com.flance.assessment.dto.request;

import com.flance.assessment.model.PaymentGateway;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FundWalletRequest {
    private String accountNumber;
    private PaymentGateway paymentGateway;
    private BigDecimal amount;
}
