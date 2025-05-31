package com.example.prescription.management.system.controller;

import com.example.prescription.management.system.helper.RegistrationDataValidation;
import com.example.prescription.management.system.model.dto.DoctorRegistrationDto;
import com.example.prescription.management.system.model.dto.JwtAuthenticationResponseDto;
import com.example.prescription.management.system.model.dto.PatientRegistrationDto;
import com.example.prescription.management.system.model.dto.SignInRequestDto;
import com.example.prescription.management.system.model.entity.MyUser;
import com.example.prescription.management.system.model.entity.Role;
import com.example.prescription.management.system.model.mapper.DoctorMapper;
import com.example.prescription.management.system.model.mapper.PatientMapper;
import com.example.prescription.management.system.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {
    private final AuthenticationService authenticationService;
    private final PatientMapper patientMapper;
    private final DoctorMapper doctorMapper;
    private final RegistrationDataValidation registrationDataValidation;

    @PostMapping("/registration-Patient")
    private ResponseEntity<?> registrationPatient(@RequestBody PatientRegistrationDto dto) {
        try {
            String validationResult = registrationDataValidation.patientRegistrationDataValidation(dto);
            if(!validationResult.equals("valid"))
                return ResponseEntity.badRequest().body(Map.of("message", validationResult));
            MyUser user = patientMapper.mapToEntity(dto);
            user = authenticationService.sinUp(user,"patient");
            if(user != null) {
                Set<String> roles = new HashSet<>();
                for(Role role : user.getRoles()) {
                    roles.add(role.getName());
                }
                dto.setPatientAge(Period.between(dto.getPatientBirthDate(), LocalDate.now()).getYears());
                dto.setRoles(roles);
                return ResponseEntity.ok(dto);
            }
            else return ResponseEntity.badRequest().body(Map.of("message", "Server error, Patient not save"));
        }catch (Exception e){
            System.out.println("Exception form public controller = "+e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/registration-doctor")
    private ResponseEntity<?> registrationDoctor(@RequestBody DoctorRegistrationDto dto) {
        try {
            String validationResult = registrationDataValidation.doctorRegistrationDataValidation(dto);
            if(!validationResult.equals("valid"))
                return ResponseEntity.badRequest().body(Map.of("message", validationResult));
            MyUser user = doctorMapper.mapToEntity(dto);
            user = authenticationService.sinUp(user,"doctor");
            if(user != null) {
                Set<String> roles = new HashSet<>();
                for(Role role : user.getRoles()) {
                    roles.add(role.getName());
                }
                dto.setRoles(roles);
                return ResponseEntity.ok(dto);
            }
            else return ResponseEntity.badRequest().body(Map.of("message", "Server error, Patient not save"));
        }catch (Exception e){
            System.out.println("Exception form public controller = "+e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody SignInRequestDto dto) {
        JwtAuthenticationResponseDto responseDto = authenticationService.signIn(dto);
        if(responseDto != null) {
            return ResponseEntity.ok(responseDto);
        }else {return ResponseEntity.badRequest().body(Map.of("message", "Invalid username or password"));}
    }
}
