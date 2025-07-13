package com.anshumanprajapati.project.uber.uberApp.dto;

import com.anshumanprajapati.project.uber.uberApp.enums.Role;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String name;
    private String email;
    private Set<Role> roles;
}
