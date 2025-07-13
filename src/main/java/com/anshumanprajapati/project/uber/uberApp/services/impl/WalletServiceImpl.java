package com.anshumanprajapati.project.uber.uberApp.services.impl;

import com.anshumanprajapati.project.uber.uberApp.entities.*;
import com.anshumanprajapati.project.uber.uberApp.enums.TransactionMethod;
import com.anshumanprajapati.project.uber.uberApp.enums.TransactionType;
import com.anshumanprajapati.project.uber.uberApp.repositories.WalletRepository;
import com.anshumanprajapati.project.uber.uberApp.services.WalletService;
import com.anshumanprajapati.project.uber.uberApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet = walletRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Wallet not found for user with ID: " + user.getId()));

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .amount(amount)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .build();


        wallet.setBalance(wallet.getBalance() + amount);
        wallet.getTransactions().add(walletTransaction);
        return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet = walletRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Wallet not found for user with ID: " + user.getId()));

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .amount(amount)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(transactionMethod)
                .build();
        wallet.setBalance(wallet.getBalance() - amount);
        wallet.getTransactions().add(walletTransaction);
        return walletRepository.save(wallet);
    }



    @Override
    public double getWalletBalance() {
//        Wallet wallet = walletRepository.findByUser(user)
//                .orElseThrow(() -> new RuntimeException("Wallet not found for user with ID: " + user.getId()));
//        return wallet.getBalance();
        return 0.0;
    }



    @Override
    public Wallet createNewWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        return walletRepository.save(wallet);

    }

    @Override
    public void withdrawMoneyFromWallet(Driver driver) {

    }

    @Override
    public Wallet getWalletByUser(User user) {
        return walletRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Wallet not found for user with ID: " + user.getId()));
    }
}
