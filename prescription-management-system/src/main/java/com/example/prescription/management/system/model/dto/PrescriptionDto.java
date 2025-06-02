package com.example.prescription.management.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PrescriptionDto {
    @Schema(hidden = true)
    private Long prescriptionId;
    private LocalDate prescriptionDate; // validation
    private String patientName; // not null
    private int patientAge; // valid age range, not null
    private String patientGender; //select box, mandatory, not null
    private String patientDiagnosis; // text area
    private String patientMedicines; // text area
    private LocalDate nextVisitDate; // valid optional
}
