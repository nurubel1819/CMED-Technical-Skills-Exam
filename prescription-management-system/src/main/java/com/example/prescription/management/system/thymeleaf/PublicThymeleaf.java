package com.example.prescription.management.system.thymeleaf;

import com.example.prescription.management.system.helper.RegistrationDataValidation;
import com.example.prescription.management.system.helper.UploadDummyData;
import com.example.prescription.management.system.jwt.JwtUtils;
import com.example.prescription.management.system.model.dto.DoctorRegistrationDto;
import com.example.prescription.management.system.model.dto.JwtAuthenticationResponseDto;
import com.example.prescription.management.system.model.dto.PatientRegistrationDto;
import com.example.prescription.management.system.model.dto.SignInRequestDto;
import com.example.prescription.management.system.model.entity.MyUser;
import com.example.prescription.management.system.model.entity.Role;
import com.example.prescription.management.system.model.mapper.DoctorMapper;
import com.example.prescription.management.system.model.mapper.PatientMapper;
import com.example.prescription.management.system.service.AuthenticationService;
import com.example.prescription.management.system.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping()
@RequiredArgsConstructor
public class PublicThymeleaf {

    private final RegistrationDataValidation registrationDataValidation;
    private final DoctorMapper doctorMapper;
    private final PatientMapper patientMapper;
    private final AuthenticationService authenticationService;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final UploadDummyData uploadDummyData;


    @GetMapping("/")
    public String home(Model model) {
        return "HomePage";
    }

    @GetMapping("/upload-dummy-data")
    public String uploadDummyData(Model model) {
        uploadDummyData.uploadSomeUser();
        return "redirect:/login";
    }
    @GetMapping("/registration-doctor") //----------------------- Doctor Registration -----------------------
    public String registrationDoctor(Model model) {
        model.addAttribute("DoctorRegistration",new DoctorRegistrationDto());
        return "DoctorRegistration";
    }
    @PostMapping("/registration-doctor")
    public String registrationDoctorPost(DoctorRegistrationDto dto) {
        try {
            String validationResult = registrationDataValidation.doctorRegistrationDataValidation(dto);
            if(!validationResult.equals("valid"))
                return "redirect:/registration-doctor?message="+validationResult;
            MyUser user = doctorMapper.mapToEntity(dto);
            user = authenticationService.sinUp(user,"doctor");
            if(user != null) {
                return "redirect:/login?message=Doctor registration successful";
            }
            else return "redirect:/registration-doctor?message=Server error, Doctor not save";
        }catch (Exception e){
            System.out.println("Exception form public controller = "+e.getMessage());
            return "redirect:/registration-doctor?message="+e.getMessage();
        }
    }
    @GetMapping("/registration-patient") // -------------------- Patient Registration ----------------------
    public String registrationPatient(Model model) {
        model.addAttribute("PatientRegistration",new PatientRegistrationDto());
        return "PatientRegistration";
    }
    @PostMapping("/registration-patient")
    public String registrationPatientPost(PatientRegistrationDto dto) {
        try {
            String validationResult = registrationDataValidation.patientRegistrationDataValidation(dto);
            if(!validationResult.equals("valid"))
                return "redirect:/registration-patient?message="+validationResult;
            MyUser user = patientMapper.mapToEntity(dto);
            user = authenticationService.sinUp(user,"patient");
            if(user != null) {
                return "redirect:/login?message=Patient registration successful";
            }
            else return "redirect:/registration-patient?message=Server error, Patient not save";
        }catch (Exception e){
            System.out.println("Exception form public controller = "+e.getMessage());
            return "redirect:/registration-patient?message="+e.getMessage();
        }
    }

    @GetMapping("/login") //--------------------------------- User Login ----------------------------------
    public String login(Model model, HttpServletRequest request) {
        model.addAttribute("SignInRequestDto",new SignInRequestDto());

        String token = jwtUtils.getJwtFromCookies(request);
        if(token!=null) {
            try {
                String phone = jwtUtils.extractUsername(token);
                MyUser user = userService.findUserByPhone(phone);
                if (user == null) {
                    return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
                } else {
                    //Extract role from token
                    List<String> roles = jwtUtils.extractRoles(token);

                    //Role-based redirect
                    if (roles.contains("ADMIN")) {
                        return "redirect:/admin/dashboard";
                    } else if (roles.contains("DOCTOR")) {
                        return "redirect:/doctor/my-prescriptions";
                    } else if (roles.contains("PATIENT")) {
                        return "redirect:/patient/dashboard";
                    } else {
                        return "redirect:/login?message=Invalid token";
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception = " + e.getMessage());
                return "redirect:/login?message=Login failed. Please check your phone and password, try again.";
            }
        }
        return "LoginPage";
    }
    @PostMapping("/login")
    public String loginPost(@ModelAttribute("SignInRequestDto") SignInRequestDto dto, HttpServletResponse response) {
        dto.setPhone(dto.getPhone().replace(" ", "")); // remove space
        dto.setPassword(dto.getPassword().replace(" ", ""));
        try {
            JwtAuthenticationResponseDto status = authenticationService.signIn(dto);
            if (status.getToken() != null) {
                // Set JWT in  cookie
                boolean tokenSetStatus = jwtUtils.setJwtToCookies(response, status.getToken());
                if (!tokenSetStatus)
                    return "redirect:/login?message=Login failed. Please check your phone and password, try again.";

                //Extract role from token
                List<String> roles = jwtUtils.extractRoles(status.getToken());

                //Role-based redirect
                if (roles.contains("ADMIN")) {
                    return "redirect:/admin/dashboard";
                } else if (roles.contains("DOCTOR")) {
                    return "redirect:/doctor/my-prescriptions";
                } else if (roles.contains("PATIENT")) {
                    return "redirect:/patient/dashboard";
                } else {
                    return "redirect:/login?message=Invalid token";
                }
            }
            else return "redirect:/login?message=Login failed. Please check your phone and password, try again.";
        }catch (Exception e){
            System.out.println("Exception = "+e.getMessage());
            return "redirect:/login?message=Login failed. Please check your phone and password, try again.";
        }
    }
    @GetMapping("/user-logout")
    public String logout(HttpServletResponse response) {
        // remove JWT from cookie
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)  //  give 0 then cookie will be deleted immediately
                .sameSite("Lax")
                .secure(false)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return "redirect:/login?message=You are logged out";
    }
}
