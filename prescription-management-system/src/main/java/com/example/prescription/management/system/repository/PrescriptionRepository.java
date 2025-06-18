package com.example.prescription.management.system.repository;

import com.example.prescription.management.system.model.dto.PrescriptionCountPerDayDto;
import com.example.prescription.management.system.model.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findAllByPrescriptionDateBetween(LocalDate startDate, LocalDate endDate);
    List<Prescription> findByDoctorIdAndPrescriptionDateBetween(Long doctorId, LocalDate startDate, LocalDate endDate);

    List<Prescription> findAllByDoctorId(Long doctorId);

    @Query("SELECT new com.example.prescription.management.system.model.dto." +
            "PrescriptionCountPerDayDto(p.prescriptionDate, COUNT(p)) " +
            "FROM Prescription p " +
            "WHERE p.doctorId = :doctorId " +          // doctor → doctorId
            "GROUP BY p.prescriptionDate " +
            "ORDER BY p.prescriptionDate ASC")
    List<PrescriptionCountPerDayDto> countPrescriptionsByDateForDoctor(@Param("doctorId") Long doctorId);


}
