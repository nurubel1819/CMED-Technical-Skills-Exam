package com.example.prescription.management.system.helper;

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
}
