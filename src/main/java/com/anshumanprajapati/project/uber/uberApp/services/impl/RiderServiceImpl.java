package com.anshumanprajapati.project.uber.uberApp.services.impl;

import com.anshumanprajapati.project.uber.uberApp.dto.RideDto;
import com.anshumanprajapati.project.uber.uberApp.dto.RideRequestDto;
import com.anshumanprajapati.project.uber.uberApp.dto.RiderDto;
import com.anshumanprajapati.project.uber.uberApp.entities.Driver;
import com.anshumanprajapati.project.uber.uberApp.entities.RideRequest;
import com.anshumanprajapati.project.uber.uberApp.entities.Rider;
import com.anshumanprajapati.project.uber.uberApp.entities.User;
import com.anshumanprajapati.project.uber.uberApp.enums.RideRequestStatus;
import com.anshumanprajapati.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.anshumanprajapati.project.uber.uberApp.repositories.RideRequestRepository;
import com.anshumanprajapati.project.uber.uberApp.repositories.RiderRepository;
import com.anshumanprajapati.project.uber.uberApp.services.RiderService;
import com.anshumanprajapati.project.uber.uberApp.strategies.DriverMatchingStrategy;
import com.anshumanprajapati.project.uber.uberApp.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {


    private final ModelMapper modelMapper;

    private final RideFareCalculationStrategy rideFareCalculationStrategy;

    private final DriverMatchingStrategy driverMatchingStrategy;

    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;

    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        Rider rider = getCurrentRider();
        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);

        double fare = rideFareCalculationStrategy.calculateFare(rideRequest);
        rideRequest.setFare(fare);
        rideRequest.setRider(rider);

        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

        List<Driver> drivers = driverMatchingStrategy.findMatchingDriver(rideRequest);

        return modelMapper.map(savedRideRequest, RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public RideDto rateDriver(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        return null;
    }

    @Override
    public List<RideDto> getAllMyRides() {
        return List.of();
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
