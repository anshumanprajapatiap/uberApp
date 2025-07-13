package com.anshumanprajapati.project.uber.uberApp.strategies.impl;

import com.anshumanprajapati.project.uber.uberApp.entities.Driver;
import com.anshumanprajapati.project.uber.uberApp.entities.Payment;
import com.anshumanprajapati.project.uber.uberApp.entities.Rider;
import com.anshumanprajapati.project.uber.uberApp.enums.PaymentStatus;
import com.anshumanprajapati.project.uber.uberApp.enums.TransactionMethod;
import com.anshumanprajapati.project.uber.uberApp.repositories.PaymentRepository;
import com.anshumanprajapati.project.uber.uberApp.services.WalletService;
import com.anshumanprajapati.project.uber.uberApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();
        Rider rider = payment.getRide().getRider();

        double platformCommissionFee = payment.getAmount() * PLATFORM_FEE;
        double earnedAmount = payment.getAmount() - platformCommissionFee;

        walletService.deductMoneyFromWallet(rider.getUser(), payment.getAmount(), null,
                payment.getRide(), TransactionMethod.RIDE);

        walletService.addMoneyToWallet(driver.getUser(), earnedAmount, null,
                payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
