package com.anshumanprajapati.project.uber.uberApp.strategies;

import com.anshumanprajapati.project.uber.uberApp.entities.Driver;
import com.anshumanprajapati.project.uber.uberApp.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {

    List<Driver> findMatchingDriver(RideRequest rideRequest);
}
