package com.example.prescription.management.system.model.dto;

import java.util.List;

public record ExternalPageDto(
        List<ExternalDataDto> data,
        int currentPage,
        int totalPages) {}

