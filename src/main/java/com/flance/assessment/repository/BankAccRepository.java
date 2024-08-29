package com.flance.assessment.repository;

import com.flance.assessment.model.BankAccount;
import com.flance.assessment.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankAccRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByAccountNumberAndBank(String accountNumber, String bank);
    BankAccount findByid(Long walletId);

    Optional<BankAccount> findByAccountNumber(String accountNumber);

}
