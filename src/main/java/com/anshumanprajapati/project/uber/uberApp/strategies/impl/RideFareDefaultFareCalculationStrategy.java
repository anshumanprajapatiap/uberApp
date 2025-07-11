package com.anshumanprajapati.project.uber.uberApp.strategies.impl;


import com.anshumanprajapati.project.uber.uberApp.entities.RideRequest;
import com.anshumanprajapati.project.uber.uberApp.services.DistanceService;
import com.anshumanprajapati.project.uber.uberApp.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideFareDefaultFareCalculationStrategy implements RideFareCalculationStrategy  {

    private final DistanceService distanceService;

    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distance = distanceService.calculateDistance(rideRequest.getPickupLocation(), rideRequest.getDropOffLocation());
        return  distance * RIDE_RATE;
    }
}
