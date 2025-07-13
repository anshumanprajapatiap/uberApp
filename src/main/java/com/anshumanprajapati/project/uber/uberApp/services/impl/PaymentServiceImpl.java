package com.anshumanprajapati.project.uber.uberApp.services.impl;

import com.anshumanprajapati.project.uber.uberApp.entities.Payment;
import com.anshumanprajapati.project.uber.uberApp.entities.Ride;
import com.anshumanprajapati.project.uber.uberApp.enums.PaymentStatus;
import com.anshumanprajapati.project.uber.uberApp.repositories.PaymentRepository;
import com.anshumanprajapati.project.uber.uberApp.services.PaymentService;
import com.anshumanprajapati.project.uber.uberApp.strategies.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;

    @Override
    public void processPayment(Ride ride) {
        Payment payment = paymentRepository.findByRide(ride).orElseThrow(
                () -> new IllegalArgumentException("Payment not found for the ride with id: " + ride.getId()));
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethod())
                .processPayment(payment);
    }

    @Override
    public Payment createPayment(Ride ride) {
       Payment payment = Payment.builder()
                .ride(ride)
                .amount(ride.getFare())
                .paymentStatus(PaymentStatus.PENDING)
                .paymentMethod(ride.getPaymentMethod())
                .build();
        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus status) {
        payment.setPaymentStatus(status);
        paymentRepository.save(payment);
    }
}
