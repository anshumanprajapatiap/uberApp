package com.anshumanprajapati.project.uber.uberApp.strategies;

import com.anshumanprajapati.project.uber.uberApp.entities.RideRequest;


public interface RideFareCalculationStrategy {
    double RIDE_RATE = 10.0;
    double calculateFare(RideRequest rideRequest);
}
