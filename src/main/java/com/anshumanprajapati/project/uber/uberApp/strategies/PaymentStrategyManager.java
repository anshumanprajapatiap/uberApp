package com.anshumanprajapati.project.uber.uberApp.strategies;

import com.anshumanprajapati.project.uber.uberApp.enums.PaymentMethod;
import com.anshumanprajapati.project.uber.uberApp.strategies.impl.CashPaymentStrategy;
import com.anshumanprajapati.project.uber.uberApp.strategies.impl.WalletPaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentStrategyManager {

    private final WalletPaymentStrategy walletPaymentStrategy;
    private final CashPaymentStrategy cashPaymentStrategy;


    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod) {
        return switch (paymentMethod) {
            case CASH -> cashPaymentStrategy;
            case WALLET -> walletPaymentStrategy;
        };
    }
}
