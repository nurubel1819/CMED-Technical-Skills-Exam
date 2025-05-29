package com.example.prescription.management.system.controller;

import com.example.prescription.management.system.model.dto.JwtAuthenticationResponseDto;
import com.example.prescription.management.system.model.dto.SignInRequestDto;
import com.example.prescription.management.system.model.dto.UserDto;
import com.example.prescription.management.system.model.entity.MyUser;
import com.example.prescription.management.system.model.entity.Role;
import com.example.prescription.management.system.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    private ResponseEntity<?> registration(@RequestBody UserDto dto) {
        MyUser user = new MyUser();
        user.setPhone(dto.getPhone());
        user.setPassword(dto.getPassword());
        user = authenticationService.sinUp(user);
        if(user != null) {
            Set<String> roles = new HashSet<>();
            for(Role role : user.getRoles()) {
                roles.add(role.getName());
            }
            dto.setRoles(roles);
            return ResponseEntity.ok(dto);
        }
        else {return ResponseEntity.badRequest().body(Map.of("message", "User not added"));}
    }
    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody SignInRequestDto dto) {
        JwtAuthenticationResponseDto responseDto = authenticationService.signIn(dto);
        if(responseDto != null) {
            return ResponseEntity.ok(responseDto);
        }else {return ResponseEntity.badRequest().body(Map.of("message", "Invalid username or password"));}
    }
}
