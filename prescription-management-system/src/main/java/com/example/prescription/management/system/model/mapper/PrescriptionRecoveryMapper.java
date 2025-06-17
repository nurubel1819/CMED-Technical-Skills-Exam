package com.example.prescription.management.system.model.mapper;

import com.example.prescription.management.system.model.dto.PrescriptionDto;
import com.example.prescription.management.system.model.entity.PrescriptionRecovery;
import org.springframework.stereotype.Component;

@Component
public class PrescriptionRecoveryMapper {

    public PrescriptionRecovery mapToEntity(PrescriptionDto dto,String doctorName) {
        PrescriptionRecovery prescription = new PrescriptionRecovery();
        prescription.setPrescriptionDate(dto.getPrescriptionDate());
        prescription.setPatientName(dto.getPatientName());
        prescription.setPatientAge(dto.getPatientAge());
        prescription.setPatientGender(dto.getPatientGender());
        prescription.setPatientDiagnosis(dto.getPatientDiagnosis());
        prescription.setPatientMedicines(dto.getPatientMedicines());
        prescription.setNextVisitDate(dto.getNextVisitDate());
        prescription.setDoctorName(doctorName);
        return prescription;
    }
}
