package com.example.prescription.management.system.thymeleaf;

import com.example.prescription.management.system.model.dto.DoctorRegistrationDto;
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

    @GetMapping("/registration-doctor")
    public String registrationDoctor(Model model) {
        model.addAttribute("DoctorRegistration",new DoctorRegistrationDto());
        return "DoctorRegistration";
    }
}
