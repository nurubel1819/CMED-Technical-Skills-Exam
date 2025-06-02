package com.example.prescription.management.system.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionFilterDto {
    private LocalDate startDate;
    private LocalDate endDate;
}
