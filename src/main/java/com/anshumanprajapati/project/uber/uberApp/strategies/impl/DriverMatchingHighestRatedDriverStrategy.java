package com.anshumanprajapati.project.uber.uberApp.strategies.impl;

import com.anshumanprajapati.project.uber.uberApp.entities.Driver;
import com.anshumanprajapati.project.uber.uberApp.entities.RideRequest;
import com.anshumanprajapati.project.uber.uberApp.repositories.DriverRepository;
import com.anshumanprajapati.project.uber.uberApp.strategies.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverMatchingHighestRatedDriverStrategy implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;
    /**
     * This strategy finds the top 10 nearest drivers based on their rating.
     * It uses the pickup location from the ride request to find the nearest drivers.
     *
     * @param rideRequest The ride request containing the pickup location.
     * @return A list of drivers sorted by their rating.
     */
    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {
        return driverRepository.findTenNearbyTopRatedDrivers(rideRequest.getPickupLocation());
    }
}
