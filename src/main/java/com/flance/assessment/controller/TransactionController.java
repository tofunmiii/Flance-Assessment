package com.flance.assessment.controller;

import com.flance.assessment.dto.request.FundWalletRequest;
import com.flance.assessment.dto.request.GetTransactionsRequest;
import com.flance.assessment.dto.response.FundWalletResponse;
import com.flance.assessment.dto.response.GetTransactionsResponse;
import com.flance.assessment.model.BankAccount;
import com.flance.assessment.model.PaymentGateway;
import com.flance.assessment.model.Transaction;
import com.flance.assessment.model.Wallet;
import com.flance.assessment.repository.TransactionRepository;
import com.flance.assessment.repository.WalletRepository;
import com.flance.assessment.service.TransactionService;
import com.flance.assessment.service.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/fund")
    public ResponseEntity<FundWalletResponse> fundWallet(@RequestBody @Validated FundWalletRequest request , HttpServletRequest servletRequest) {
        ResponseEntity<FundWalletResponse> transaction = transactionService.fundWallet(request,servletRequest);
        if (transaction != null) {
            return ResponseEntity.ok(transaction.getBody());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/getAll")
    public ResponseEntity<GetTransactionsResponse> getTransactions(@RequestBody  GetTransactionsRequest request, HttpServletRequest servletRequest) {
       return transactionService.getTransactions(request, servletRequest);
    }

    }
