package com.flance.assessment.controller;

import com.flance.assessment.dto.request.CreateWalletRequest;
import com.flance.assessment.dto.request.FindBankRequest;
import com.flance.assessment.dto.request.FindWalletRequest;
import com.flance.assessment.dto.request.LinkBankAccountRequest;
import com.flance.assessment.dto.response.CreateWalletResponse;
import com.flance.assessment.dto.response.FindBankResponse;
import com.flance.assessment.dto.response.FindWalletResponse;
import com.flance.assessment.dto.response.LinkBankAccountResponse;
import com.flance.assessment.model.BankAccount;
import com.flance.assessment.repository.BankAccRepository;
import com.flance.assessment.repository.WalletRepository;
import com.flance.assessment.service.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class WalletController {
    private final WalletService walletService;


    @PostMapping(value = "/create-Wallet")
    public ResponseEntity<CreateWalletResponse> createWallet(@RequestBody CreateWalletRequest request, HttpServletRequest servletRequest){
        return walletService.createWallet(request, servletRequest);
    }
    @PostMapping("/get-Wallet")
    public ResponseEntity<FindWalletResponse> findWalletByEmail(@RequestBody FindWalletRequest request, HttpServletRequest servletRequest) {
        return walletService.findWalletByEmail(request, servletRequest);
    }
    @PostMapping("/link-Account")
    public ResponseEntity<LinkBankAccountResponse> linkBankAccount(@RequestBody LinkBankAccountRequest request, HttpServletRequest servletRequest) throws Exception {
        return walletService.linkBankAccount(request, servletRequest);
    }

    @PostMapping("/find-bank")
    public ResponseEntity<FindBankResponse> findBank(@RequestBody FindBankRequest request, HttpServletRequest servletRequest) {
        return walletService.getBankAccountsByWallet(request, servletRequest);
    }

}
