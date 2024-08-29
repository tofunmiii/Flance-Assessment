package com.flance.assessment.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    private PaymentGateway paymentGateway;

}
