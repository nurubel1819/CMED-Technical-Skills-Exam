package com.example.prescription.management.system.thymeleaf;

import com.example.prescription.management.system.model.dto.DoctorRegistrationDto;
import com.example.prescription.management.system.model.dto.PatientRegistrationDto;
import com.example.prescription.management.system.model.dto.SignInRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
@RequiredArgsConstructor
public class PublicThymeleaf {

    @GetMapping("/")
    public String home(Model model) {
        return "HomePage";
    }

    @GetMapping("/registration-doctor") //----------------------- Doctor Registration -----------------------
    public String registrationDoctor(Model model) {
        model.addAttribute("DoctorRegistration",new DoctorRegistrationDto());
        return "DoctorRegistration";
    }

    @GetMapping("/login") //--------------------------------- User Login ----------------------------------
    public String login(Model model) {
        model.addAttribute("SignInRequestDto",new SignInRequestDto());
        return "LoginPage";
    }

    @GetMapping("/registration-patient") // -------------------- Patient Registration ----------------------
    public String registrationPatient(Model model) {
        model.addAttribute("PatientRegistration",new PatientRegistrationDto());
        return "PatientRegistration";
    }
}
