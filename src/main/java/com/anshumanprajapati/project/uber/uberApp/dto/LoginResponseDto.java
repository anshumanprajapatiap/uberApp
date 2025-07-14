package com.anshumanprajapati.project.uber.uberApp.dto;

import com.anshumanprajapati.project.uber.uberApp.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
