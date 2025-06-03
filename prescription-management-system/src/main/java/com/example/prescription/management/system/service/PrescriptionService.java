package com.example.prescription.management.system.service;

import com.example.prescription.management.system.model.dto.PrescriptionCountPerDayDto;
import com.example.prescription.management.system.model.entity.MyUser;
import com.example.prescription.management.system.model.entity.Prescription;

import java.time.LocalDate;
import java.util.List;

public interface PrescriptionService {
    Prescription savePrescription(Prescription prescription, MyUser doctor);
    Prescription updatePrescription(Prescription prescription);
    Prescription findPrescriptionById(Long id);
    List<Prescription> findAllPrescriptionByDoctor(MyUser doctor);
    String deletePrescriptionById(Long id);
    List<Prescription> findAllPrescriptions();
    List<Prescription> findAllPrescriptionBetweenDate(LocalDate startDate, LocalDate endDate);
    List<Prescription> findOneDoctorAllPrescriptionInDateRange(MyUser doctor,LocalDate startDate, LocalDate endDate);
    List<PrescriptionCountPerDayDto> countPrescriptionsByDateForDoctor(MyUser doctor);
}
