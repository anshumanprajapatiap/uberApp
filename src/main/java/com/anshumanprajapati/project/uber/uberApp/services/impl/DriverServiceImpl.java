package com.anshumanprajapati.project.uber.uberApp.services.impl;

import com.anshumanprajapati.project.uber.uberApp.dto.DriverDto;
import com.anshumanprajapati.project.uber.uberApp.dto.RideDto;
import com.anshumanprajapati.project.uber.uberApp.dto.RiderDto;
import com.anshumanprajapati.project.uber.uberApp.entities.Driver;
import com.anshumanprajapati.project.uber.uberApp.entities.Payment;
import com.anshumanprajapati.project.uber.uberApp.entities.Ride;
import com.anshumanprajapati.project.uber.uberApp.entities.RideRequest;
import com.anshumanprajapati.project.uber.uberApp.enums.RideRequestStatus;
import com.anshumanprajapati.project.uber.uberApp.enums.RideStatus;
import com.anshumanprajapati.project.uber.uberApp.repositories.DriverRepository;
import com.anshumanprajapati.project.uber.uberApp.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;;
    private final RatingService ratingService;

    @Override
    @Transactional
    public RideDto acceptRide(Long rideRequestId) {

        RideRequest rideRequest = rideRequestService.findRideRequestById(rideRequestId);

        if (!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)) {
            throw new IllegalStateException("Ride request is not in pending status");
        }
        Driver currentDriver = getCurrentDriver();
        if (!currentDriver.getAvailable()) {
            throw new IllegalStateException("Driver is not available to accept the ride");
        }

        Driver savedDriver = updateDriverAvailability(currentDriver, false);

        Ride ride = rideService.createNewRide(rideRequest, savedDriver);
        return modelMapper.map(ride, RideDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver currentDriver = getCurrentDriver();

        if(!currentDriver.equals(ride.getDriver())) {
            throw new IllegalStateException("This ride does not belong to the current driver");
        }
        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new IllegalStateException("Ride is not in a cancellable status: " + ride.getRideStatus());
        }

        rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        updateDriverAvailability(currentDriver, true);

        return modelMapper.map(ride, RideDto.class);
    }

    @Override
    @Transactional
    public RideDto startRide(Long rideId, String otp) {
        Ride ride = rideService.getRideById(rideId);
        Driver currentDriver = getCurrentDriver();

        if(!currentDriver.equals(ride.getDriver())) {
            throw new IllegalStateException("This ride does not belong to the current driver");
        }
        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new IllegalStateException("Ride is not in confirmed status");
        }
        if (!ride.getOtp().equals(otp)) {
            throw new IllegalStateException("Invalid OTP provided");
        }
        ride.setStartedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ONGOING);
        paymentService.createPayment(savedRide);
        ratingService.createNewRating(savedRide);

        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    @Transactional
    public RideDto endRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver currentDriver = getCurrentDriver();

        if(!currentDriver.equals(ride.getDriver())) {
            throw new IllegalStateException("This ride does not belong to the current driver");
        }
        if (!ride.getRideStatus().equals(RideStatus.ONGOING)) {
            throw new IllegalStateException("Ride is not in confirmed status");
        }


        ride.setEndedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ENDED);
        Driver savedDriver = updateDriverAvailability(currentDriver, true);

        paymentService.processPayment(ride);

        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Driver currentDriver = getCurrentDriver();

        if(!currentDriver.equals(ride.getDriver())) {
            throw new IllegalStateException("This ride does not belong to the current driver");
        }
        if (!ride.getRideStatus().equals(RideStatus.ENDED)) {
            throw new IllegalStateException("Ride is not in confirmed status");
        }
        return ratingService.rateRider(ride, rating);
    }

    @Override
    public DriverDto getMyProfile() {
        Driver currentDriver = getCurrentDriver();
        return modelMapper.map(currentDriver, DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Driver currentDriver = getCurrentDriver();
        return rideService.getAllRidesOfDriver(currentDriver, pageRequest).map(
                ride -> modelMapper.map(ride, RideDto.class)
        );
    }

    @Override
    public Driver getCurrentDriver(){
        return driverRepository.findById(2L)
                .orElseThrow(() -> new IllegalStateException("Driver not found"));
    }

    @Override
    public Driver updateDriverAvailability(Driver driver, boolean available) {
        driver.setAvailable(available);
        return driverRepository.save(driver);
    }

    @Override
    public Driver createNewDriver(Driver createDriver) {
       return driverRepository.save(createDriver);
    }
}
