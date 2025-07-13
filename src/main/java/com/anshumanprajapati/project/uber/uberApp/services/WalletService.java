package com.anshumanprajapati.project.uber.uberApp.services;

import com.anshumanprajapati.project.uber.uberApp.entities.Driver;
import com.anshumanprajapati.project.uber.uberApp.entities.Ride;
import com.anshumanprajapati.project.uber.uberApp.entities.User;
import com.anshumanprajapati.project.uber.uberApp.entities.Wallet;
import com.anshumanprajapati.project.uber.uberApp.enums.TransactionMethod;

public interface WalletService {


    Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod);

    Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod);

    double getWalletBalance();

    Wallet createNewWallet(User user);

    void withdrawMoneyFromWallet(Driver driver);

    Wallet getWalletByUser(User user);

}
