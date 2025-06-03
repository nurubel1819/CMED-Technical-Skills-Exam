package com.example.prescription.management.system.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalDataDto {
    private Long userId;
    private Long id;
    private String title;
    private String body;
}
