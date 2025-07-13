package com.anshumanprajapati.project.uber.uberApp.strategies;

import com.anshumanprajapati.project.uber.uberApp.entities.Payment;

public interface PaymentStrategy {

    static final Double PLATFORM_FEE = 0.3;
    void processPayment(Payment payment);
}
