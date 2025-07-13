package com.anshumanprajapati.project.uber.uberApp.dto;

import lombok.*;

import java.util.List;

@Data
public class WalletDto {

    private Long id;

    private UserDto user;

    private Double balance;

    private List<WalletTransactionDto> transactions;
}
