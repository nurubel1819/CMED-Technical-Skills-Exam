package com.example.prescription.management.system.helper;

import com.example.prescription.management.system.model.dto.DoctorRegistrationDto;
import com.example.prescription.management.system.model.dto.PatientRegistrationDto;
import com.example.prescription.management.system.model.dto.PrescriptionDto;
import com.example.prescription.management.system.model.entity.MyUser;
import com.example.prescription.management.system.model.entity.Prescription;
import com.example.prescription.management.system.model.mapper.DoctorMapper;
import com.example.prescription.management.system.model.mapper.PatientMapper;
import com.example.prescription.management.system.model.mapper.PrescriptionMapper;
import com.example.prescription.management.system.model.mapper.PrescriptionRecoveryMapper;
import com.example.prescription.management.system.repository.PrescriptionRecoveryRepository;
import com.example.prescription.management.system.repository.UserRepository;
import com.example.prescription.management.system.service.AuthenticationService;
import com.example.prescription.management.system.service.PrescriptionService;
import com.example.prescription.management.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class UploadDummyData {
    private final UserRepository userRepository;
    private final PatientMapper patientMapper;
    private final DoctorMapper doctorMapper;
    private final PrescriptionMapper prescriptionMapper;
    private final PrescriptionService prescriptionService;
    private final AuthenticationService authenticationService;
    private final PrescriptionRecoveryRepository prescriptionRecoveryRepository;
    private final PrescriptionRecoveryMapper  prescriptionRecoveryMapper;

    public void uploadSomeUser(){
        if(userRepository.count()==0)
        {
            // create first patient (First user is default admin)
            PatientRegistrationDto patientDto = new PatientRegistrationDto();
            patientDto.setPatientPhone("01753278407");
            patientDto.setPatientPassword("123");
            patientDto.setPatientName("Nasir Uddin");
            patientDto.setPatientEmail("nurubel70@gmail.com");
            patientDto.setPatientBirthDate(LocalDate.of(1999,11,19));
            patientDto.setPatientGender("Male");
            patientDto.setPatientAddress("60/19-G,Dalpur,Dhaka");
            patientDto.setPatientDiseaseHistory("No history");

            MyUser user = patientMapper.mapToEntity(patientDto);
            authenticationService.sinUp(user,"PATIENT");

            //create first  doctor
            DoctorRegistrationDto doctorDto = new DoctorRegistrationDto();
            doctorDto.setDoctorPhone("01753278408");
            doctorDto.setPassword("123");
            doctorDto.setDoctorName("Farzana Sultana");
            doctorDto.setDoctorEmail("farzana@gmail.com");
            doctorDto.setDateOfBirth(LocalDate.of(1992,7,22));
            doctorDto.setDoctorGender("Female");
            doctorDto.setDoctorAddress("3/510,Monohorjang,Cumilla");
            doctorDto.setSpecialization("Cardiologist");
            doctorDto.setExperience("5 years");
            doctorDto.setQualification("MBBS");

            MyUser doctor = doctorMapper.mapToEntity(doctorDto);
            doctor = authenticationService.sinUp(doctor,"DOCTOR");

            //create only patient role
            patientDto.setPatientPhone("01749402012");
            patientDto.setPatientPassword("123");
            patientDto.setPatientName("Nasima Begum");
            patientDto.setPatientEmail("nasime@gmail.com");
            patientDto.setPatientBirthDate(LocalDate.of(1982,11,19));
            patientDto.setPatientGender("Female");
            patientDto.setPatientAddress("60/19-G,Dalpur,Dhaka");
            patientDto.setPatientDiseaseHistory("blood sugar high");

            user = patientMapper.mapToEntity(patientDto);
            authenticationService.sinUp(user,"PATIENT");

            //create only patient role
            patientDto.setPatientPhone("01710148905");
            patientDto.setPatientPassword("123");
            patientDto.setPatientName("Ali Azgar");
            patientDto.setPatientEmail("azgar@gmail.com");
            patientDto.setPatientBirthDate(LocalDate.of(1972,11,19));
            patientDto.setPatientGender("Male");
            patientDto.setPatientAddress("Darachow, Monohorjang, Cumilla");
            patientDto.setPatientDiseaseHistory("high blood pressure");

            user = patientMapper.mapToEntity(patientDto);
            authenticationService.sinUp(user,"PATIENT");

            // now write some prescription
            // create patient 1
            PrescriptionDto prescriptionDto = new PrescriptionDto();
            prescriptionDto.setPrescriptionDate(LocalDate.of(2025,6,2));
            prescriptionDto.setPatientName("Anisul Islam");
            prescriptionDto.setPatientAge(25);
            prescriptionDto.setPatientGender("Male");
            prescriptionDto.setPatientDiagnosis("High blood pressure");
            prescriptionDto.setPatientMedicines("Bisoren 2.5 mg, Sergel 20 mg");
            prescriptionDto.setNextVisitDate(LocalDate.of(2025,6,4));

            Prescription prescription = prescriptionMapper.mapToEntity(prescriptionDto);
            prescriptionService.savePrescription(prescription,doctor);

            prescriptionRecoveryRepository.save(prescriptionRecoveryMapper.mapToEntity(prescriptionDto,doctor.getName()));

            //create patient 2
            prescriptionDto.setPrescriptionDate(LocalDate.of(2025,6,2));
            prescriptionDto.setPatientName("Zakir Khan");
            prescriptionDto.setPatientAge(26);
            prescriptionDto.setPatientGender("Male");
            prescriptionDto.setPatientDiagnosis("Liver failure");
            prescriptionDto.setPatientMedicines("Napa 500 mg, Nexum 20 mg");
            prescriptionDto.setNextVisitDate(LocalDate.of(2025,6,5));

            prescription = prescriptionMapper.mapToEntity(prescriptionDto);
            prescriptionService.savePrescription(prescription,doctor);

            prescriptionRecoveryRepository.save(prescriptionRecoveryMapper.mapToEntity(prescriptionDto,doctor.getName()));

            //create patient 3
            prescriptionDto.setPrescriptionDate(LocalDate.of(2025,6,3));
            prescriptionDto.setPatientName("Asma Akter");
            prescriptionDto.setPatientAge(24);
            prescriptionDto.setPatientGender("Female");
            prescriptionDto.setPatientDiagnosis("Fiver");
            prescriptionDto.setPatientMedicines("No medicines needed");

            prescription = prescriptionMapper.mapToEntity(prescriptionDto);
            prescriptionService.savePrescription(prescription,doctor);

            prescriptionRecoveryRepository.save(prescriptionRecoveryMapper.mapToEntity(prescriptionDto,doctor.getName()));

            //create patient 4
            prescriptionDto.setPrescriptionDate(LocalDate.of(2025,6,3));
            prescriptionDto.setPatientName("Monir Khan");
            prescriptionDto.setPatientAge(35);
            prescriptionDto.setPatientGender("Male");
            prescriptionDto.setPatientDiagnosis("Kidney failure");
            prescriptionDto.setPatientMedicines("Omidon 10 mg");
            prescriptionDto.setNextVisitDate(LocalDate.of(2025,6,6));

            prescription = prescriptionMapper.mapToEntity(prescriptionDto);
            prescriptionService.savePrescription(prescription,doctor);

            prescriptionRecoveryRepository.save(prescriptionRecoveryMapper.mapToEntity(prescriptionDto,doctor.getName()));

            //create patient 5
            prescriptionDto.setPrescriptionDate(LocalDate.of(2025,6,1));
            prescriptionDto.setPatientName("Shorna Akter");
            prescriptionDto.setPatientAge(16);
            prescriptionDto.setPatientGender("Female");
            prescriptionDto.setPatientDiagnosis("Fiver");
            prescriptionDto.setPatientMedicines("Napa 500 mg");
            prescriptionDto.setNextVisitDate(LocalDate.of(2025,6,10));

            prescription = prescriptionMapper.mapToEntity(prescriptionDto);
            prescriptionService.savePrescription(prescription,doctor);

            prescriptionRecoveryRepository.save(prescriptionRecoveryMapper.mapToEntity(prescriptionDto,doctor.getName()));

            //create second doctor
            doctorDto.setDoctorPhone("01753278406");
            doctorDto.setPassword("123");
            doctorDto.setDoctorName("Mamun");
            doctorDto.setDoctorEmail("mamun@gmail.com");
            doctorDto.setDateOfBirth(LocalDate.of(1985,7,22));
            doctorDto.setDoctorGender("Male");
            doctorDto.setDoctorAddress("Moakhali,Dhaka");
            doctorDto.setSpecialization("Neurologist");
            doctorDto.setExperience("10 years");
            doctorDto.setQualification("MBBS,FCPS");

            doctor = doctorMapper.mapToEntity(doctorDto);
            doctor = authenticationService.sinUp(doctor,"DOCTOR");

            //create patient 6
            prescriptionDto.setPrescriptionDate(LocalDate.of(2025,6,3));
            prescriptionDto.setPatientName("Shorna Akter Moni");
            prescriptionDto.setPatientAge(17);
            prescriptionDto.setPatientGender("Female");
            prescriptionDto.setPatientDiagnosis("Fiver");
            prescriptionDto.setPatientMedicines("Napa 500 mg");
            prescriptionDto.setNextVisitDate(LocalDate.of(2025,6,10));

            prescription = prescriptionMapper.mapToEntity(prescriptionDto);
            prescriptionService.savePrescription(prescription,doctor);

            prescriptionRecoveryRepository.save(prescriptionRecoveryMapper.mapToEntity(prescriptionDto,doctor.getName()));
        }
    }
}
