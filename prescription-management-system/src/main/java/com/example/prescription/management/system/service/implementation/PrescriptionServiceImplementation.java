package com.example.prescription.management.system.service.implementation;

import com.example.prescription.management.system.model.entity.MyUser;
import com.example.prescription.management.system.model.entity.Prescription;
import com.example.prescription.management.system.repository.PrescriptionRepository;
import com.example.prescription.management.system.repository.UserRepository;
import com.example.prescription.management.system.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImplementation implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final UserRepository userRepository;


    @Override
    public Prescription savePrescription(Prescription prescription, MyUser doctor) {
        try {
            prescription.setDoctor(doctor);
            prescription = prescriptionRepository.save(prescription);
            doctor.getPrescriptions().add(prescription);
            userRepository.save(doctor);
            return prescription;
        }catch (Exception e) {
            System.out.println("Exception from PrescriptionServiceImplementation.savePrescription = "+e.getMessage());
            return null;
        }
    }

    @Override
    public Prescription findPrescriptionById(Long id) {
        return prescriptionRepository.findById(id).orElse(null);
    }

    @Override
    public String deletePrescriptionById(Long id) {
        try {
            prescriptionRepository.deleteById(id);
            return "Prescription deleted successfully";
        }catch (Exception e) {
            System.out.println("Exception from PrescriptionServiceImplementation.deletePrescriptionById = "+e.getMessage());
            return "Server error, Prescription not deleted";
        }
    }

    @Override
    public List<Prescription> findAllPrescriptions() {
        try {
            return prescriptionRepository.findAll();
        }catch (Exception e) {
            System.out.println("Exception from PrescriptionServiceImplementation.findAllPrescriptions = "+e.getMessage());
            return null;
        }
    }

    @Override
    public List<Prescription> findAllPrescriptionsBetweenDateRange(LocalDate startDate, LocalDate endDate) {
        try {
            return prescriptionRepository.findAllByPrescriptionDateBetween(startDate, endDate);
        }catch (Exception e) {
            System.out.println("Exception from PrescriptionServiceImplementation.findAllPrescriptionsBetweenDateRange = "+e.getMessage());
            return null;
        }
    }
}
