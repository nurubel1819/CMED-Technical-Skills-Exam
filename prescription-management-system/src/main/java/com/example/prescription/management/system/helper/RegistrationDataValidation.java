package com.example.prescription.management.system.helper;

import com.example.prescription.management.system.model.dto.DoctorRegistrationDto;
import com.example.prescription.management.system.model.dto.PatientRegistrationDto;
import com.example.prescription.management.system.model.dto.PrescriptionDto;
import com.example.prescription.management.system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationDataValidation {

    private final DataValidation dataValidation;
    private final UserRepository userRepository;

    public String patientRegistrationDataValidation(PatientRegistrationDto dto){
        if(!dataValidation.isValidBDPhone(dto.getPatientPhone())) return "Phone number is not valid";
        if(dto.getPatientEmail()!=null && !dto.getPatientEmail().isEmpty() && !dataValidation.isValidEmail(dto.getPatientEmail())) return "Email is not valid";
        if(!dto.getPatientPassword().equals(dto.getPatientConfirmPassword())) return "password and confirm password is not same";
        if(dto.getPatientPhone().length()>11) dto.setPatientPhone(dto.getPatientPhone().substring(3));
        if(userRepository.findByPhone(dto.getPatientPhone()).isPresent()) return "Phone number already exist";
        if(userRepository.findByEmail(dto.getPatientEmail()).isPresent()) return "Email already exist";
        if(!dataValidation.isValidGender(dto.getPatientGender())) return "Gender is not valid";
        if(!dataValidation.isValidBirthDate(dto.getPatientBirthDate())) return "Birth date is not valid, it must be small today";
        else return "valid";
    }
    public String doctorRegistrationDataValidation(DoctorRegistrationDto dto){
        if(!dataValidation.isValidBDPhone(dto.getDoctorPhone())) return "Phone number is not valid";
        if(dto.getDoctorEmail()!=null && !dto.getDoctorEmail().isEmpty() && !dataValidation.isValidEmail(dto.getDoctorEmail())) return "Email is not valid";
        if(!dto.getPassword().equals(dto.getConfirmPassword())) return "password and confirm password is not same";
        if(dto.getDoctorPhone().length()>11) dto.setDoctorPhone(dto.getDoctorPhone().substring(3));
        if(userRepository.findByPhone(dto.getDoctorPhone()).isPresent()) return "Phone number already exist";
        if(userRepository.findByEmail(dto.getDoctorEmail()).isPresent()) return "Email already exist";
        if(!dataValidation.isValidGender(dto.getDoctorGender())) return "Gender is not valid";
        if(!dataValidation.isValidBirthDate(dto.getDateOfBirth())) return "Birth date is not valid, it must be small today";
        else return "valid";
    }
    public String prescriptionValidation(PrescriptionDto dto){
        if(!dataValidation.isDateToday(dto.getPrescriptionDate())) return "Prescription date must be today";
        if(!dataValidation.isValidGender(dto.getPatientGender())) return "Gender is not valid";
        if(dto.getNextVisitDate()!=null && !dataValidation.isValidFutureDate(dto.getNextVisitDate())) return "Next visit date is not valid, it must be future date";

        else return "valid";
    }
    public String doctorEditDataValidation(DoctorRegistrationDto dto){
        if(!dataValidation.isValidGender(dto.getDoctorGender())) return "Gender is not valid";
        if(!dataValidation.isValidBirthDate(dto.getDateOfBirth())) return "Birth date is not valid, it must be small today";
        else return "valid";
    }
}
