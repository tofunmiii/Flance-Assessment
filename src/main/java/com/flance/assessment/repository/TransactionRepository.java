package com.flance.assessment.repository;

import com.flance.assessment.model.Transaction;
import com.flance.assessment.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByWalletId(Long walletId);

}
