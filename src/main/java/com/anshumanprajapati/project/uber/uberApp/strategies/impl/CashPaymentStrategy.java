package com.anshumanprajapati.project.uber.uberApp.strategies.impl;

import com.anshumanprajapati.project.uber.uberApp.entities.Driver;
import com.anshumanprajapati.project.uber.uberApp.entities.Payment;
import com.anshumanprajapati.project.uber.uberApp.enums.PaymentStatus;
import com.anshumanprajapati.project.uber.uberApp.enums.TransactionMethod;
import com.anshumanprajapati.project.uber.uberApp.repositories.PaymentRepository;
import com.anshumanprajapati.project.uber.uberApp.services.WalletService;
import com.anshumanprajapati.project.uber.uberApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();

        double platformCommissionFee = payment.getAmount() * PLATFORM_FEE;
        walletService.deductMoneyFromWallet(driver.getUser(), platformCommissionFee, null,
                payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
