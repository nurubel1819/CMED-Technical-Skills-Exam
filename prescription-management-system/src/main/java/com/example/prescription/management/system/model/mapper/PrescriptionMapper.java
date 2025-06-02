package com.example.prescription.management.system.model.mapper;

import com.example.prescription.management.system.model.dto.PrescriptionDto;
import com.example.prescription.management.system.model.entity.Prescription;
import org.springframework.stereotype.Component;

@Component
public class PrescriptionMapper {
    public Prescription mapToEntity(PrescriptionDto dto) {
        Prescription prescription = new Prescription();
        prescription.setPrescriptionDate(dto.getPrescriptionDate());
        prescription.setPatientName(dto.getPatientName());
        prescription.setPatientAge(dto.getPatientAge());
        prescription.setPatientGender(dto.getPatientGender());
        prescription.setPatientDiagnosis(dto.getPatientDiagnosis());
        prescription.setPatientMedicines(dto.getPatientMedicines());
        prescription.setNextVisitDate(dto.getNextVisitDate());
        return prescription;
    }
    public PrescriptionDto mapToDto(Prescription prescription) {
        PrescriptionDto dto = new PrescriptionDto();
        dto.setPrescriptionDate(prescription.getPrescriptionDate());
        dto.setPatientName(prescription.getPatientName());
        dto.setPatientAge(prescription.getPatientAge());
        dto.setPatientGender(prescription.getPatientGender());
        dto.setPatientDiagnosis(prescription.getPatientDiagnosis());
        dto.setPatientMedicines(prescription.getPatientMedicines());
        dto.setNextVisitDate(prescription.getNextVisitDate());
        return dto;
    }
    public Prescription updatePrescriptionInfo(Prescription prescription, PrescriptionDto dto) {
        prescription.setPrescriptionDate(dto.getPrescriptionDate());
        prescription.setPatientName(dto.getPatientName());
        prescription.setPatientAge(dto.getPatientAge());
        prescription.setPatientGender(dto.getPatientGender());
        prescription.setPatientDiagnosis(dto.getPatientDiagnosis());
        prescription.setPatientMedicines(dto.getPatientMedicines());
        prescription.setNextVisitDate(dto.getNextVisitDate());
        return prescription;
    }
}
