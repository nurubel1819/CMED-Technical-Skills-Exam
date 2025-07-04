package com.example.prescription.management.system.model.mapper;

import com.example.prescription.management.system.model.dto.DoctorRegistrationDto;
import com.example.prescription.management.system.model.entity.MyUser;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {
    public MyUser mapToEntity(DoctorRegistrationDto dto)
    {
        // if +88 is present in phone then that's remove form prefix
        if(dto.getDoctorPhone().length()>11) dto.setDoctorPhone(dto.getDoctorPhone().substring(3));
        MyUser user = new MyUser();
        user.setPhone(dto.getDoctorPhone());
        user.setPassword(dto.getPassword());
        user.setName(dto.getDoctorName());
        if(dto.getDoctorEmail().isEmpty()) dto.setDoctorEmail(null);
        user.setEmail(dto.getDoctorEmail());
        user.setBirthday(dto.getDateOfBirth());
        user.setGender(dto.getDoctorGender());
        user.setAddress(dto.getDoctorAddress());
        user.setSpecialization(dto.getSpecialization());
        user.setExperience(dto.getExperience());
        user.setQualification(dto.getQualification());

        return user;
    }
    public DoctorRegistrationDto mapToDto(MyUser user)
    {
        DoctorRegistrationDto dto = new DoctorRegistrationDto();
        dto.setDoctorId(dto.getDoctorId());
        dto.setDoctorPhone(user.getPhone());
        dto.setPassword(user.getPassword());
        dto.setDoctorName(user.getName());
        dto.setDoctorEmail(user.getEmail());
        dto.setDateOfBirth(user.getBirthday());
        dto.setDoctorGender(user.getGender());
        dto.setDoctorAddress(user.getAddress());
        dto.setSpecialization(user.getSpecialization());
        dto.setExperience(user.getExperience());
        dto.setQualification(user.getQualification());
        return dto;
    }
    public MyUser mapToUpdateEntity(MyUser user, DoctorRegistrationDto dto)
    {
        user.setName(dto.getDoctorName());
        user.setBirthday(dto.getDateOfBirth());
        user.setGender(dto.getDoctorGender());
        user.setAddress(dto.getDoctorAddress());
        user.setSpecialization(dto.getSpecialization());
        user.setExperience(dto.getExperience());
        user.setQualification(dto.getQualification());

        return user;
    }
}
