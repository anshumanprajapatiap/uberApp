package com.anshumanprajapati.project.uber.uberApp.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto {

    private Long id;
    private UserDto user;
    private Double rating;
    private Boolean available;
    private String vehicleId;
}
