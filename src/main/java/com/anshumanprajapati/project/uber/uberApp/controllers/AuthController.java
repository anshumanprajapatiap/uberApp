package com.anshumanprajapati.project.uber.uberApp.controllers;


import com.anshumanprajapati.project.uber.uberApp.dto.*;
import com.anshumanprajapati.project.uber.uberApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto){
        return authService.login(loginDto.getEmail(), loginDto.getPassword());
    }
    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignupDto signupDto){
        return authService.signup(signupDto);
    }
    @PostMapping("/onBoardNewDriver/{userId}")
    public ResponseEntity<DriverDto> onBoardNewDriver(@PathVariable Long userId, @RequestBody OnboardDriverDto onboardDriverDto){
        DriverDto driver =  authService.onboardNewDriver(userId, onboardDriverDto.getVehicleId());
        return new ResponseEntity<>(driver, HttpStatus.CREATED);
    }
}
