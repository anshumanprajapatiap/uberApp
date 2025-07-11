package com.anshumanprajapati.project.uber.uberApp.controllers;


import com.anshumanprajapati.project.uber.uberApp.dto.RideRequestDto;
import com.anshumanprajapati.project.uber.uberApp.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rider")
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;

    @PostMapping("/requestRide")
    public ResponseEntity<RideRequestDto> requestRide(@RequestBody RideRequestDto rideRequestDto){
        RideRequestDto rideRequestDtoRes =  riderService.requestRide(rideRequestDto);
        return new ResponseEntity<>(rideRequestDtoRes, HttpStatus.OK);
    }
}
