package com.anshumanprajapati.project.uber.uberApp.services;

import com.anshumanprajapati.project.uber.uberApp.dto.DriverDto;
import com.anshumanprajapati.project.uber.uberApp.dto.LoginResponseDto;
import com.anshumanprajapati.project.uber.uberApp.dto.SignupDto;
import com.anshumanprajapati.project.uber.uberApp.dto.UserDto;

public interface AuthService {

    LoginResponseDto login(String email, String password);

    UserDto signup(SignupDto signupDto);

    DriverDto onboardNewDriver(Long userId, String vehicleId);

    String refreshToken(String refreshToken);
}
