package com.flance.assessment.service.impl;

import com.flance.assessment.config.HeaderBase;
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
import com.flance.assessment.util.Validator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final WalletService walletService;

    /**
     * Processes a request to fund a wallet with a specific amount via a payment gateway.
     *
     * @param request        The request containing details for funding the wallet.
     * @param servletRequest The HTTP servlet request.
     * @return A ResponseEntity containing a FundWalletResponse with the result of the funding operation.
     */
    @Override
    public ResponseEntity<FundWalletResponse> fundWallet( @Validated FundWalletRequest request , HttpServletRequest servletRequest)  {
        HeaderBase headerBase =new HeaderBase();
        FundWalletResponse response =new FundWalletResponse();
        Optional<BankAccount> bankAccount = walletService.getBankAccountByAccountNumber(request.getAccountNumber());

        try {

            if (!bankAccount.isPresent()) {
                headerBase.setResponseCode("404");
                headerBase.setResponseMessage("Bank Account not found");
                response.setHeaderBase(headerBase);
                return ResponseEntity.badRequest().body(response);
            }
            if (!isValidPaymentGateway(request.getPaymentGateway())) {
                headerBase.setResponseCode("404");
                headerBase.setResponseMessage("Payment Gateway not found");
                response.setHeaderBase(headerBase);
                return ResponseEntity.badRequest().body(response);
            }

            headerBase.setResponseCode("200");
            headerBase.setResponseMessage("Fund wallet successful");
            Wallet wallet = bankAccount.get().getWallet();
            response.setHeaderBase(headerBase);
            response.setTransaction(processPayment(wallet, bankAccount.get(), request.getAmount(), request.getPaymentGateway()));
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            headerBase.setResponseCode("500");
            headerBase.setResponseMessage("Internal server error");
            log.error("Error linking bank account: {}", e.getMessage());
            response.setHeaderBase(headerBase);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    /**
     * Retrieves all transactions associated with a given wallet ID.
     *
     * @param request        The request containing the wallet ID to retrieve transactions for.
     * @param servletRequest The HTTP servlet request.
     * @return A ResponseEntity containing a GetTransactionsResponse with the list of transactions.
     */
    @Override
    public ResponseEntity<GetTransactionsResponse> getTransactions(GetTransactionsRequest request, HttpServletRequest servletRequest) {
        HeaderBase headerBase = new HeaderBase();
        GetTransactionsResponse response = new GetTransactionsResponse();

        List<Transaction> transactions = transactionRepository.findByWalletId(request.getWalletId());

        try{
            Optional<Wallet> wallet = walletRepository.findById(request.getWalletId());
            if (wallet.isEmpty()) {
                headerBase.setResponseMessage("Wallet not found for the given ID");
                headerBase.setResponseCode("404");
                response.setHeaderBase(headerBase);
                return ResponseEntity.status(404).body(response);
            }
            if (transactions.isEmpty()) {
                headerBase.setResponseMessage("No transactions found for the given wallet ID");
                headerBase.setResponseCode("404");
                response.setHeaderBase(headerBase);
                return ResponseEntity.badRequest().body(response);
            }
            headerBase.setResponseMessage("All transactions retrieved successfully");
            headerBase.setResponseCode("200");
            response.setHeaderBase(headerBase);
            response.setTransactionList(transactions);

            return ResponseEntity.ok().body(response);

        }catch (Exception e ){
            headerBase.setResponseCode("500");
            headerBase.setResponseMessage("Internal server error");
            log.error("Error linking bank account: {}", e.getMessage());
            response.setHeaderBase(headerBase);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Processes a payment by simulating the payment via the given payment gateway.
     *
     * @param wallet        The wallet to which the payment should be made.
     * @param bankAccount   The bank account from which the payment is made.
     * @param amount        The amount to be paid.
     * @param paymentGateway The payment gateway to process the payment.
     * @return The created Transaction object.
     */
    private Transaction processPayment(Wallet wallet, BankAccount bankAccount, BigDecimal amount, PaymentGateway paymentGateway) {
        simulatePayment(paymentGateway);
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setWallet(wallet);
        transaction.setPaymentGateway(paymentGateway);
        return transactionRepository.save(transaction);
    }
    private boolean isValidPaymentGateway(PaymentGateway paymentGateway) {
        return paymentGateway == PaymentGateway.FLUTTERWAVE || paymentGateway == PaymentGateway.PAYSTACK;
    }
    /**
     * Simulates a payment process for the provided payment gateway.
     *
     * @param paymentGateway The payment gateway to simulate the payment for.
     */
    private void simulatePayment(PaymentGateway paymentGateway) {
        switch (paymentGateway) {
            case FLUTTERWAVE:
                log.info("Simulating payment via Flutterwave...");
                break;
            case PAYSTACK:
                log.info("Simulating payment via Paystack...");
                break;
            default:
                log.info("Simulating payment via \" + paymentGateway + \"...");
                break;
        }
    }
}
