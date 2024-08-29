package com.flance.assessment.service;

import com.flance.assessment.dto.request.FundWalletRequest;
import com.flance.assessment.dto.request.GetTransactionsRequest;
import com.flance.assessment.dto.response.FundWalletResponse;
import com.flance.assessment.dto.response.GetTransactionsResponse;
import com.flance.assessment.model.BankAccount;
import com.flance.assessment.model.PaymentGateway;
import com.flance.assessment.model.Transaction;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface TransactionService {
     public ResponseEntity<FundWalletResponse> fundWallet(FundWalletRequest request , HttpServletRequest servletRequest) ;
     public ResponseEntity<GetTransactionsResponse> getTransactions(GetTransactionsRequest request , HttpServletRequest servletRequest);
}
