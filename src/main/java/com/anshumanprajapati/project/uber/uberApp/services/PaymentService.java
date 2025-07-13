package com.anshumanprajapati.project.uber.uberApp.services;

import com.anshumanprajapati.project.uber.uberApp.entities.Payment;
import com.anshumanprajapati.project.uber.uberApp.entities.Ride;
import com.anshumanprajapati.project.uber.uberApp.enums.PaymentStatus;

public interface PaymentService {

    void processPayment(Ride ride);

    Payment createPayment(Ride ride);

    void updatePaymentStatus(Payment payment, PaymentStatus status);
}
