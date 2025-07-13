package com.anshumanprajapati.project.uber.uberApp.strategies.impl;

import com.anshumanprajapati.project.uber.uberApp.entities.RideRequest;
import com.anshumanprajapati.project.uber.uberApp.services.DistanceService;
import com.anshumanprajapati.project.uber.uberApp.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy  {

    private final DistanceService distanceService;
    private static final double SURGE_MULTIPLIER = 1.5;

    /**
     * This method calculates the fare for a ride request based on surge pricing.
     * Surge pricing is typically applied during high demand periods.
     *
     * @param rideRequest The ride request containing details like distance, time, and surge multiplier.
     * @return The calculated fare based on surge pricing.
     */
    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distanceInKm = distanceService.calculateDistance(rideRequest.getPickupLocation(), rideRequest.getDropOffLocation());
        return distanceInKm * RIDE_RATE * SURGE_MULTIPLIER;
    }
}
