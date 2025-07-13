package com.anshumanprajapati.project.uber.uberApp.dto;

import com.anshumanprajapati.project.uber.uberApp.entities.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiderDto {

    private User user;
    private Double rating;
}
