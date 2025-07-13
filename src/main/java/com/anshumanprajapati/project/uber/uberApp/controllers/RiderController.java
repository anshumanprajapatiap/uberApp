package com.anshumanprajapati.project.uber.uberApp.controllers;


import com.anshumanprajapati.project.uber.uberApp.dto.*;
import com.anshumanprajapati.project.uber.uberApp.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/riders")
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;

    @PostMapping("/requestRide")
    public ResponseEntity<RideRequestDto> requestRide(@RequestBody RideRequestDto rideRequestDto){
        RideRequestDto rideRequestDtoRes =  riderService.requestRide(rideRequestDto);
        return new ResponseEntity<>(rideRequestDtoRes, HttpStatus.OK);
    }

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDto> cancelRide(@PathVariable Long rideId ){
        RideDto rideDto =  riderService.cancelRide(rideId);
        return new ResponseEntity<>(rideDto, HttpStatus.OK);
    }

    @PostMapping("/rateDriver")
    public ResponseEntity<DriverDto> rateDriver(@RequestBody RatingDto ratingDto) {
        DriverDto driver =  riderService.rateDriver(ratingDto.getRideId(), ratingDto.getRating());
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }

    @GetMapping("/myProfile")
    public ResponseEntity<RiderDto> getMyProfile() {
        RiderDto riderDto = riderService.getMyProfile();
        return new ResponseEntity<>(riderDto, HttpStatus.OK);
    }

    @GetMapping("/myRides")
    public ResponseEntity<Page<RideDto>> getAllMyRides(@RequestParam(defaultValue = "0") Integer pageOffset,
                                                       @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageOffset, pageSize,
                Sort.by(Sort.Direction.DESC, "createdTime", "id"));
        Page<RideDto> rides = riderService.getAllMyRides(pageRequest);
        return new ResponseEntity<>(rides, HttpStatus.OK);
    }

}
