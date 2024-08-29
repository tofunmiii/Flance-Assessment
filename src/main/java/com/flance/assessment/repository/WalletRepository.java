package com.flance.assessment.repository;

import com.flance.assessment.model.BankAccount;
import com.flance.assessment.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByEmail (String email);
    Wallet findByid(Long walletId);

}
