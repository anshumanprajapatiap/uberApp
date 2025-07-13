package com.anshumanprajapati.project.uber.uberApp.services.impl;

import com.anshumanprajapati.project.uber.uberApp.dto.RideDto;
import com.anshumanprajapati.project.uber.uberApp.dto.RideRequestDto;
import com.anshumanprajapati.project.uber.uberApp.dto.RiderDto;
import com.anshumanprajapati.project.uber.uberApp.entities.*;
import com.anshumanprajapati.project.uber.uberApp.enums.RideRequestStatus;
import com.anshumanprajapati.project.uber.uberApp.enums.RideStatus;
import com.anshumanprajapati.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.anshumanprajapati.project.uber.uberApp.repositories.RideRequestRepository;
import com.anshumanprajapati.project.uber.uberApp.repositories.RiderRepository;
import com.anshumanprajapati.project.uber.uberApp.services.DriverService;
import com.anshumanprajapati.project.uber.uberApp.services.RideService;
import com.anshumanprajapati.project.uber.uberApp.services.RiderService;
import com.anshumanprajapati.project.uber.uberApp.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideService rideService;
    private final DriverService driverService;

    @Override
    @Transactional
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        Rider rider = getCurrentRider();
        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);

        double fare = rideStrategyManager.getRideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);
        log.info("Ride request created with ID: {}", savedRideRequest.getId());


        List<Driver> drivers = rideStrategyManager.getDriverMatchingStrategy(rider.getRating()).findMatchingDriver(savedRideRequest);
        if (drivers.isEmpty()) {
            log.warn("No drivers available for the ride request.");
            throw new ResourceNotFoundException("No drivers available for the ride request.");
        }
        // TODO : Send notification to all the drivers about this ride request

        return modelMapper.map(savedRideRequest, RideRequestDto.class);
    }

    @Override
    @Transactional
    public RideDto cancelRide(Long rideId) {
        Rider rider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);

        if(!rider.equals(ride.getRider())){
            throw new IllegalStateException("Rider does not own this ride request");
        }

        if (ride.getRideStatus().equals(RideStatus.ONGOING)
                || ride.getRideStatus().equals(RideStatus.ENDED)) {
            throw new IllegalStateException("Ride cannot be cancelled as it is " + ride.getRideStatus());
        }
        driverService.updateDriverAvailability(ride.getDriver(), true);
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.CANCELLED);

        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public RideDto rateDriver(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        Rider currentRider = getCurrentRider();
        return modelMapper.map(currentRider, RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Rider currentRider = getCurrentRider();
        return rideService.getAllRidesOfRider(currentRider, pageRequest).map(
                ride -> modelMapper.map(ride, RideDto.class)
        );
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider
                .builder()
                .user(user)
                .rating(0.0)
                .build();
        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
//        TODO : implement Spring security
        return riderRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException(
                "Rider not found with id: "+1
        ));
    }
}
