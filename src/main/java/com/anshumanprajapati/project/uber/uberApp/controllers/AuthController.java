package com.anshumanprajapati.project.uber.uberApp.controllers;


import com.anshumanprajapati.project.uber.uberApp.dto.*;
import com.anshumanprajapati.project.uber.uberApp.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto,
                                                  HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String[] tokens = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        return ResponseEntity.ok(new LoginResponseDto(tokens[0]));
    }
    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignupDto signupDto){
        return authService.signup(signupDto);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/onBoardNewDriver/{userId}")
    public ResponseEntity<DriverDto> onBoardNewDriver(@PathVariable Long userId, @RequestBody OnboardDriverDto onboardDriverDto){
        DriverDto driver =  authService.onboardNewDriver(userId, onboardDriverDto.getVehicleId());
        return new ResponseEntity<>(driver, HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies()).
                filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies"));

        String accessToken = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(new LoginResponseDto(accessToken));
    }
}
