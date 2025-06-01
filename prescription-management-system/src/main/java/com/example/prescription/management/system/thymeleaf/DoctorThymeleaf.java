package com.example.prescription.management.system.thymeleaf;

import com.example.prescription.management.system.helper.PrescriptionMapper;
import com.example.prescription.management.system.helper.RegistrationDataValidation;
import com.example.prescription.management.system.jwt.JwtUtils;
import com.example.prescription.management.system.model.dto.DoctorRegistrationDto;
import com.example.prescription.management.system.model.dto.PrescriptionDto;
import com.example.prescription.management.system.model.entity.MyUser;
import com.example.prescription.management.system.model.entity.Prescription;
import com.example.prescription.management.system.service.PrescriptionService;
import com.example.prescription.management.system.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorThymeleaf {

    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final RegistrationDataValidation validation;
    private final PrescriptionMapper prescriptionMapper;
    private final PrescriptionService prescriptionService;


    @GetMapping("/dashboard")
    public String doctorDashboard(Model model, HttpServletRequest request){

        try {
            String token = jwtUtils.getJwtFromCookies(request);
            if(token!=null) {
                Long userId = jwtUtils.extractUserId(token);
                String doctorName = userService.findUserById(userId).getName();
                model.addAttribute("doctorName",doctorName);
            }
            else return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
        }catch (Exception e) {
            System.out.println("Exception from Doctor Dashboard = "+e.getMessage());
        }
        return "DoctorDashboard";
    }
    @GetMapping("/write-prescription")
    public String writePrescription(Model model){
        PrescriptionDto prescriptionDto = new PrescriptionDto();
        model.addAttribute("PrescriptionDto",prescriptionDto);
        return "PrescriptionForm";
    }
    @PostMapping("/write-prescription")
    public String writePrescription(@ModelAttribute("PrescriptionDto") PrescriptionDto dto, Model model,HttpServletRequest request){
        try {

            String jwt = jwtUtils.getJwtFromCookies(request);
            if(jwt==null) return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
            Long userId = jwtUtils.extractUserId(jwt);
            MyUser doctor = userService.findUserById(userId);

            String validationResult = validation.prescriptionValidation(dto);
            if(!validationResult.equals("valid"))
                return "redirect:/doctor/write-prescription?message="+validationResult;
            Prescription prescription = prescriptionMapper.mapToEntity(dto);
            prescription = prescriptionService.savePrescription(prescription,doctor);
            if(prescription != null) {
                return "redirect:/doctor/dashboard?message=Patient registration successful";
            }
            else return "redirect:/doctor/write-prescription?message=Server error, Patient not save";
        }catch (Exception e){
            System.out.println("Exception form public controller = "+e.getMessage());
            return "redirect:/doctor/write-prescription?message="+e.getMessage();
        }
    }
}
