package com.example.prescription.management.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class PatientRegistrationDto {
    private String patientName; // not null
    private String patientPhone;
    private String patientEmail;
    @Schema(hidden = true)
    private int patientAge; // valid age range, not null
    private String patientGender; //select box, mandatory, not null

    private String patientAddress; // text area
    private LocalDate patientBirthDate; // not null
    private String patientDiseaseHistory; // not null

    private String patientPassword;
    private String patientConfirmPassword;

    @Schema(hidden = true)
    private Set<String> roles;
}
