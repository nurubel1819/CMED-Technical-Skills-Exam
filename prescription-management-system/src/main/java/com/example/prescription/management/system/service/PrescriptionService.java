package com.example.prescription.management.system.service;

import com.example.prescription.management.system.model.entity.MyUser;
import com.example.prescription.management.system.model.entity.Prescription;

import java.time.LocalDate;
import java.util.List;

public interface PrescriptionService {
    Prescription savePrescription(Prescription prescription, MyUser doctor);
    Prescription findPrescriptionById(Long id);
    String deletePrescriptionById(Long id);
    List<Prescription> findAllPrescriptions();
    List<Prescription> findAllPrescriptionsBetweenDateRange(LocalDate startDate, LocalDate endDate);
}
