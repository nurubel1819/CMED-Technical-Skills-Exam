package com.example.prescription.management.system.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Prescription")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionId;
    @Column(nullable = false)
    private LocalDate prescriptionDate; // validation
    @Column(nullable = false)
    private String patientName; // not null
    @Column(nullable = false)
    private int patientAge; // valid age range, not null
    @Column(nullable = false)
    private String patientGender; //select box, mandatory, not null
    private String patientDiagnosis; // text area
    private String patientMedicines; // text area
    private LocalDate nextVisitDate; // valid optional

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private MyUser doctor;
}
