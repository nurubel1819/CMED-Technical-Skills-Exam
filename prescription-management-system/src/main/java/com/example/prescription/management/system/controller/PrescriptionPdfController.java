package com.example.prescription.management.system.controller;

import com.example.prescription.management.system.model.dto.PrescriptionDto;
import com.example.prescription.management.system.model.entity.Prescription;
import com.example.prescription.management.system.model.mapper.PrescriptionMapper;
import com.example.prescription.management.system.service.PdfGeneratorService;
import com.example.prescription.management.system.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PrescriptionPdfController {

    private final PdfGeneratorService pdfGeneratorService;
    private final PrescriptionMapper prescriptionMapper;
    private final PrescriptionService prescriptionService;

    @GetMapping("/doctor/download-prescription/{prescriptionId}")
    public ResponseEntity<byte[]> downloadPrescription(@PathVariable("prescriptionId") Long prescriptionId) {
        try {
            Prescription prescription = prescriptionService.findPrescriptionById(prescriptionId);

            PrescriptionDto dto = prescriptionMapper.mapToDto(prescription);
            dto.setPrescriptionId(prescriptionId);

            byte[] pdfBytes = pdfGeneratorService.generatePrescriptionPdf(dto);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=prescription_" + prescription.getPatientName() + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (Exception e) {
            System.out.println("Error while generating prescription PDF: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

