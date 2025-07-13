package com.anshumanprajapati.project.uber.uberApp.repositories;

import com.anshumanprajapati.project.uber.uberApp.entities.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {
    // Custom query methods can be defined here if needed
    // For example, to find transactions by wallet ID:
    // List<WalletTransaction> findByWalletId(Long walletId);

    // You can also define methods for filtering transactions by type, date, etc.
}