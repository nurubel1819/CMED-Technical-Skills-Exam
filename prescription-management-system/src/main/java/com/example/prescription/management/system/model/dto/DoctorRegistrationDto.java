package com.example.prescription.management.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Set;

@Data
public class DoctorRegistrationDto {
    @Schema(hidden = true)
    private Long doctorId;
    private String doctorName;
    private String doctorPhone;
    private String doctorEmail;
    private String doctorAddress;
    private String doctorGender;
    private LocalDate dateOfBirth;
    private String specialization;
    private String experience;
    private String qualification;
    private String password;
    private String confirmPassword;
    @Schema(hidden = true)
    private MultipartFile imageFile;

    @Schema(hidden = true)
    private Set<String> roles;
}
