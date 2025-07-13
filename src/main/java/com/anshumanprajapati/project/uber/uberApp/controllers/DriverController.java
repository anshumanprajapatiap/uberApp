package com.anshumanprajapati.project.uber.uberApp.controllers;

import com.anshumanprajapati.project.uber.uberApp.dto.RideDto;
import com.anshumanprajapati.project.uber.uberApp.dto.RideStartDto;
import com.anshumanprajapati.project.uber.uberApp.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PostMapping("/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDto> acceptRide(@PathVariable Long rideRequestId) {
        RideDto rideDto = driverService.acceptRide(rideRequestId);
        return ResponseEntity.ok(rideDto);
    }

    @PostMapping("/startRide/{rideRequestId}")
    public ResponseEntity<RideDto> startRide(@PathVariable Long rideRequestId,
                                                @RequestBody RideStartDto rideStartDto) {
        RideDto rideDto = driverService.startRide(rideRequestId, rideStartDto.getOtp());
        return ResponseEntity.ok(rideDto);
    }

    @PostMapping("/endRide/{rideRequestId}")
    public ResponseEntity<RideDto> endRide(@PathVariable Long rideRequestId) {
        RideDto rideDto = driverService.endRide(rideRequestId);
        return ResponseEntity.ok(rideDto);
    }

}
