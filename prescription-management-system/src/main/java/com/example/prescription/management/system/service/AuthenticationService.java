package com.example.prescription.management.system.service;


import com.example.prescription.management.system.model.dto.JwtAuthenticationResponseDto;
import com.example.prescription.management.system.model.dto.SignInRequestDto;
import com.example.prescription.management.system.model.entity.MyUser;

public interface AuthenticationService {
    MyUser sinUp(MyUser user);
    JwtAuthenticationResponseDto signIn(SignInRequestDto signInRequestDto);
}