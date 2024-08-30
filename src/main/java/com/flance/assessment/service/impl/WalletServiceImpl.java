package com.flance.assessment.service.impl;

import com.flance.assessment.config.HeaderBase;
import com.flance.assessment.dto.request.*;
import com.flance.assessment.dto.response.*;
import com.flance.assessment.model.BankAccount;
import com.flance.assessment.model.Wallet;
import com.flance.assessment.repository.BankAccRepository;
import com.flance.assessment.repository.WalletRepository;
import com.flance.assessment.service.WalletService;
import com.flance.assessment.util.Validator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final BankAccRepository bankAccRepository;

    /**
     * Creates a new wallet.
     *
     * @param request        The request containing wallet details.
     * @param servletRequest The HTTP servlet request.
     * @return A ResponseEntity containing a CreateWalletResponse with the result of the wallet creation operation.
     */
    @Override
    public ResponseEntity<CreateWalletResponse> createWallet(CreateWalletRequest request, HttpServletRequest servletRequest) {
        HeaderBase headerBase = new HeaderBase();
        CreateWalletResponse response = new CreateWalletResponse();
        Wallet wallet =new Wallet();


        boolean validatorEmail = Validator.validateEmail(request.getEmail());
        if(!validatorEmail){
            headerBase.setResponseMessage("Invalid email format");
            headerBase.setResponseCode("999");
            response.setHeaderBase(headerBase);
            log.error("Invalid Email format");
            return ResponseEntity.badRequest().body(response);
        }if ( request.getPhoneNumber().length() < 11){
            headerBase.setResponseMessage("Invalid number format");
            headerBase.setResponseCode("999");
            response.setHeaderBase(headerBase);
            log.error("Invalid number format");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            if (request.getEmail() == null || request.getPhoneNumber() == null){
                headerBase.setResponseCode("400");
                headerBase.setResponseMessage("Invalid Parameters");
                log.info("Invalid Parameters");
                response.setHeaderBase(headerBase);
                return ResponseEntity.badRequest().body(response);
            }
            Wallet existingWallet = walletRepository.findByEmail(request.getEmail());
            if (existingWallet != null) {
                headerBase.setResponseCode("400");
                headerBase.setResponseMessage("Wallet with this email already exists");
                log.info("Wallet with this email already exists: {}", request.getEmail());
                response.setHeaderBase(headerBase);
                return ResponseEntity.ok().body(response);
            }
            wallet.setEmail(request.getEmail());
            wallet.setPhoneNumber(request.getPhoneNumber());

            walletRepository.save(wallet);
            headerBase.setResponseCode("200");
            headerBase.setResponseMessage("Wallet created successfully");
            log.info("Wallet created successfully with Wallet Id: {}", wallet.getId());
            response.setHeaderBase(headerBase);
            response.setWalletId(wallet.getId());
            response.setEmail(request.getEmail());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e){
            headerBase.setResponseCode("500");
            headerBase.setResponseMessage("Internal server error");
            log.error("Error creating wallet: {}", e.getMessage());
            response.setHeaderBase(headerBase);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Finds a wallet by a given email.
     *
     * @param request        The request containing the email to search for.
     * @param servletRequest The HTTP servlet request.
     * @return A ResponseEntity containing a FindWalletResponse with the wallet details.
     */
    @Override
    public ResponseEntity<FindWalletResponse> findWalletByEmail(FindWalletRequest request, HttpServletRequest servletRequest) {
        HeaderBase headerBase = new HeaderBase();
        FindWalletResponse response = new FindWalletResponse();

        boolean validatorEmail = Validator.validateEmail(request.getEmail());
        if (!validatorEmail) {
            headerBase.setResponseMessage("Invalid email format");
            headerBase.setResponseCode("999");
            response.setHeaderBase(headerBase);
            log.error("Invalid email format: {}", request.getEmail());
            return ResponseEntity.badRequest().body(response);
        }

        try {
            if (request.getEmail() == null || request.getEmail().isEmpty()) {
                headerBase.setResponseCode("400");
                headerBase.setResponseMessage("Invalid Parameters");
                log.info("Invalid Parameters: email is null or empty");
                response.setHeaderBase(headerBase);
                return ResponseEntity.badRequest().body(response);
            }

            Wallet wallet = walletRepository.findByEmail(request.getEmail());
            if (wallet == null) {
                headerBase.setResponseCode("404");
                headerBase.setResponseMessage("No wallet found with this email");
                log.info("No wallet found with email: {}", request.getEmail());
                response.setHeaderBase(headerBase);
                return ResponseEntity.ok().body(response);
            }

            headerBase.setResponseCode("200");
            headerBase.setResponseMessage("Wallet retrieved successfully");
            response.setHeaderBase(headerBase);
            response.setEmail(request.getEmail());
            response.setWalletId(wallet.getId());
            response.setPhoneNumber(wallet.getPhoneNumber());

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            headerBase.setResponseCode("500");
            headerBase.setResponseMessage("Internal server error");
            log.error("Error retrieving wallet by email: {}", e.getMessage());
            response.setHeaderBase(headerBase);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    /**
     * Links a bank account to a wallet.
     *
     * @param request        The request containing the bank account details to link.
     * @param servletRequest The HTTP servlet request.
     * @return A ResponseEntity containing a LinkBankAccountResponse with the result of the linking operation.
     * @throws Exception If an error occurs during the linking process.
     */
    @Override
    public ResponseEntity<LinkBankAccountResponse> linkBankAccount(LinkBankAccountRequest request, HttpServletRequest servletRequest) throws Exception {
        HeaderBase headerBase = new HeaderBase();
        LinkBankAccountResponse response = new LinkBankAccountResponse();

        Validator.validateProperty(request.getBank(), "Bank cannot be empty");
        Validator.validateProperty(request.getAccountName(), "Account name cannot be empty");
        Validator.validateProperty(request.getAccountNumber(), "Account number cannot be empty");

        try {
            Wallet wallet = walletRepository.findByid(request.getWalletId());

            Optional<BankAccount> existingAccount = bankAccRepository.findByAccountNumberAndBank(request.getAccountNumber(), request.getBank());
            if (existingAccount.isPresent()) {
                headerBase.setResponseCode("400");
                headerBase.setResponseMessage("This account number is already linked to another wallet with the same bank");
                log.info("Duplicate bank account: {}", request.getAccountNumber());
                response.setHeaderBase(headerBase);
                return ResponseEntity.badRequest().body(response);
            }

            BankAccount bankAccount = new BankAccount();
            bankAccount.setAccountName(request.getAccountName());
            bankAccount.setAccountNumber(request.getAccountNumber());
            bankAccount.setBank(request.getBank());
            bankAccount.setWallet(wallet);
            bankAccRepository.save(bankAccount);

            headerBase.setResponseCode("201");
            headerBase.setResponseMessage("Bank account linked successfully");
            response.setHeaderBase(headerBase);
            response.setBank(request.getBank());
            response.setAccountNumber(request.getAccountNumber());
            response.setWalletId(request.getWalletId());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            headerBase.setResponseCode("404");
            headerBase.setResponseMessage(e.getMessage());
            log.info("Error linking bank account: {}", e.getMessage());
            response.setHeaderBase(headerBase);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception e) {
            headerBase.setResponseCode("500");
            headerBase.setResponseMessage("Internal server error");
            log.error("Error linking bank account: {}", e.getMessage());
            response.setHeaderBase(headerBase);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    /**
     * Retrieves bank accounts associated with a wallet.
     *
     * @param request        The request containing the wallet ID.
     * @param servletRequest The HTTP servlet request.
     * @return A ResponseEntity containing a FindBankResponse with the bank account details.
     */
    @Override
    public ResponseEntity<FindBankResponse> getBankAccountsByWallet(FindBankRequest request, HttpServletRequest servletRequest){
        HeaderBase headerBase = new HeaderBase();
        FindBankResponse response =new FindBankResponse();
        BankAccount existingId =  bankAccRepository.findByid(request.getWalletId());
        val b = existingId.toString();
        try{


           BankAccount bankAccount =  bankAccRepository.findByid(request.getWalletId());
           headerBase.setResponseMessage("Banks Retrieved Successfully");
           headerBase.setResponseCode("200");
           response.setHeaderBase(headerBase);
           response.setAccountName(bankAccount.getAccountName());
           response.setAccountNumber(bankAccount.getAccountNumber());
           response.setBank(bankAccount.getBank());
           return ResponseEntity.ok().body(response);
        }catch (Exception e ){
            headerBase.setResponseCode("500");
            headerBase.setResponseMessage("Internal server error");
            log.error("Error linking bank account: {}", e.getMessage());
            response.setHeaderBase(headerBase);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public Optional<BankAccount> getBankAccountByAccountNumber(String accountNumber) {
        return bankAccRepository.findByAccountNumber(accountNumber);
    }

}
