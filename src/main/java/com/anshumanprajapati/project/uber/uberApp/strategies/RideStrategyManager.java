package com.anshumanprajapati.project.uber.uberApp.strategies;

import com.anshumanprajapati.project.uber.uberApp.strategies.impl.DriverMatchingHighestRatedDriverStrategy;
import com.anshumanprajapati.project.uber.uberApp.strategies.impl.DriverMatchingNearestDriverStrategy;
import com.anshumanprajapati.project.uber.uberApp.strategies.impl.RideFareDefaultFareCalculationStrategy;
import com.anshumanprajapati.project.uber.uberApp.strategies.impl.RideFareSurgePricingFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class RideStrategyManager {

    private final DriverMatchingNearestDriverStrategy driverMatchingNearestDriverStrategy;
    private final DriverMatchingHighestRatedDriverStrategy driverMatchingHighestRatedDriverStrategy;
    private final RideFareDefaultFareCalculationStrategy rideFareDefaultFareCalculationStrategy;
    private final RideFareSurgePricingFareCalculationStrategy rideFareSurgePricingFareCalculationStrategy;

    public DriverMatchingStrategy getDriverMatchingStrategy(double riderRating) {
        if(riderRating >= 4.8) {
            return driverMatchingHighestRatedDriverStrategy;
        } else {
            return driverMatchingNearestDriverStrategy;
        }
    }

    public RideFareCalculationStrategy getRideFareCalculationStrategy() {
        LocalTime surgeStartTime = LocalTime.of(18, 0);
        LocalTime surgeEndTime = LocalTime.of(21, 0);
        LocalTime currentTime = LocalTime.now();
        boolean isSurgeTime = currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);

        if(isSurgeTime) {
            return rideFareSurgePricingFareCalculationStrategy;
        } else {
            return rideFareDefaultFareCalculationStrategy;
        }
    }

}
