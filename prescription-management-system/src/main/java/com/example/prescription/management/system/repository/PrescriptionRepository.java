package com.example.prescription.management.system.repository;

import com.example.prescription.management.system.model.dto.PrescriptionCountPerDayDto;
import com.example.prescription.management.system.model.entity.MyUser;
import com.example.prescription.management.system.model.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findAllByPrescriptionDateBetween(LocalDate startDate, LocalDate endDate);
    List<Prescription> findByDoctorAndPrescriptionDateBetween(MyUser doctor, LocalDate startDate, LocalDate endDate);
    List<Prescription> findByDoctor(MyUser doctor);

    @Query("SELECT new com.example.prescription.management.system.model.dto.PrescriptionCountPerDayDto(p.prescriptionDate, COUNT(p)) " +
            "FROM Prescription p " +
            "WHERE p.doctor = :doctor " +
            "GROUP BY p.prescriptionDate " +
            "ORDER BY p.prescriptionDate ASC")
    List<PrescriptionCountPerDayDto> countPrescriptionsByDateForDoctor(@Param("doctor") MyUser doctor);

}
