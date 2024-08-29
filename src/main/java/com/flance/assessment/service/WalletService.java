package com.flance.assessment.service;

import com.flance.assessment.dto.request.*;
import com.flance.assessment.dto.response.*;
import com.flance.assessment.model.BankAccount;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface WalletService {
     ResponseEntity<CreateWalletResponse> createWallet(CreateWalletRequest request, HttpServletRequest servletRequest);
     ResponseEntity<FindWalletResponse> findWalletByEmail(FindWalletRequest request, HttpServletRequest servletRequest);
     ResponseEntity<LinkBankAccountResponse> linkBankAccount(LinkBankAccountRequest request, HttpServletRequest servletRequest) throws Exception;
     ResponseEntity<FindBankResponse>getBankAccountsByWallet(FindBankRequest request, HttpServletRequest servletRequest ) ;
     Optional<BankAccount> getBankAccountByAccountNumber(String accountNumber);


}
