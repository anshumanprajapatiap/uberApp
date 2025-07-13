package com.anshumanprajapati.project.uber.uberApp.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class PointDto {
    private double[] coordinates;
    private String type = "Point";

    public PointDto(double[] coordinates) {
        this.coordinates = coordinates;
    }

}
