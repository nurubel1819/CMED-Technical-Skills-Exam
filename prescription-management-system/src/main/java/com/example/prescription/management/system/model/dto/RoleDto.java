package com.example.prescription.management.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    @Schema(hidden = true)
    private Long userId;
    private String name;
    @Schema(hidden = true)
    private Set<Long> users;
}
