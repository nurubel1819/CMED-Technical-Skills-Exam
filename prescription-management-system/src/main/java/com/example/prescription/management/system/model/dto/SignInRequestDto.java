package com.example.prescription.management.system.model.dto;

import lombok.Data;

@Data
public class SignInRequestDto {
    private String phone;
    private String password;
}
