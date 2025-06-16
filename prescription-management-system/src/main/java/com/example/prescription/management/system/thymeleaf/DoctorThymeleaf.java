package com.example.prescription.management.system.thymeleaf;

import com.example.prescription.management.system.helper.DataValidation;
import com.example.prescription.management.system.model.dto.*;
import com.example.prescription.management.system.model.mapper.DoctorMapper;
import com.example.prescription.management.system.model.mapper.PrescriptionMapper;
import com.example.prescription.management.system.helper.RegistrationDataValidation;
import com.example.prescription.management.system.jwt.JwtUtils;
import com.example.prescription.management.system.model.entity.MyUser;
import com.example.prescription.management.system.model.entity.Prescription;
import com.example.prescription.management.system.repository.PrescriptionRepository;
import com.example.prescription.management.system.repository.UserRepository;
import com.example.prescription.management.system.service.ExternalApiService;
import com.example.prescription.management.system.service.PrescriptionService;
import com.example.prescription.management.system.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Controller
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorThymeleaf {

    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final RegistrationDataValidation validation;
    private final DataValidation dataValidation;
    private final PrescriptionMapper prescriptionMapper;
    private final PrescriptionService prescriptionService;
    private final ExternalApiService externalApiService;
    private final DoctorMapper doctorMapper;
    private final UserRepository userRepository;


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
    @GetMapping("/my-prescriptions") //----------------- See My Prescriptions ----------------------
    public String myPrescriptions(Model model, HttpServletRequest request) {
        //return loadPrescriptions(LocalDate.now().minusMonths(1), LocalDate.now(), model, request);
        return loadPrescriptions(LocalDate.now().withDayOfMonth(1),LocalDate.now(),model,request);
    }

    @PostMapping("/my-prescriptions")
    public String myPrescriptionsPost(@ModelAttribute("filterDto") PrescriptionFilterDto filterDto, Model model, HttpServletRequest request) {
        /*if (filterDto.getStartDate().isAfter(filterDto.getEndDate()) ||
                filterDto.getStartDate().isAfter(LocalDate.now()) ||
                filterDto.getEndDate().isAfter(LocalDate.now()))
            return "redirect:/doctor/my-prescriptions?message=Invalid date range. Please check your date range and try again.";
        */
        if(filterDto == null) filterDto = new PrescriptionFilterDto(LocalDate.now().minusMonths(1), LocalDate.now() );
        System.out.println("data = "+filterDto);
        return loadPrescriptions(filterDto.getStartDate(), filterDto.getEndDate(), model, request);
    }

    //  Common logic method for /doctor prescriptions filtering
    private String loadPrescriptions(LocalDate startDate, LocalDate endDate, Model model, HttpServletRequest request) {
        try {
            String token = jwtUtils.getJwtFromCookies(request);
            if (token != null) {
                Long userId = jwtUtils.extractUserId(token);
                MyUser doctor = userService.findUserById(userId);
                String doctorName = doctor.getName();
                model.addAttribute("doctorName", doctorName);

                PrescriptionFilterDto filterDto = new PrescriptionFilterDto(startDate, endDate);
                model.addAttribute("filterDto", filterDto);

                List<Prescription> myPrescriptions = prescriptionService.findOneDoctorAllPrescriptionInDateRange(doctor, startDate, endDate);
                model.addAttribute("myPrescriptions", myPrescriptions);

                return "PrescriptionsTable";
            } else {
                return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
            }
        } catch (Exception e) {
            System.out.println("Exception from Doctor Thymeleaf = " + e.getMessage());
            return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
        }
    }
    @GetMapping("/edit-prescription/{prescriptionId}") //-------------- Edit Prescription ----------------------
    public String editPrescription(@PathVariable("prescriptionId") Long prescriptionId, Model model){
        try {
            Prescription prescription = prescriptionService.findPrescriptionById(prescriptionId);
            PrescriptionDto prescriptionDto = prescriptionMapper.mapToDto(prescription);
            prescriptionDto.setPrescriptionId(prescriptionId);
            model.addAttribute("PrescriptionDto",prescriptionDto);
            //return "EditPrescription";
            return "EditPrescription";
        }catch (Exception e){
            System.out.println("Exception form Doctor Thymeleaf get edit mehto = "+e.getMessage());
            return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
        }
    }
    @PostMapping("/update-prescription")
    public String updatePrescription(@ModelAttribute("PrescriptionDto") PrescriptionDto dto, Model model,HttpServletRequest request){
        System.out.println("id = "+dto.getPrescriptionId()+" Date = "+dto.getPrescriptionDate()+"Name = "+dto.getPatientName());
        try {
            String validationResult = validation.prescriptionValidation(dto);
            if(!validationResult.equals("valid"))
                return "redirect:/doctor/write-prescription?message="+validationResult;

            Prescription prescription = prescriptionService.findPrescriptionById(dto.getPrescriptionId());
            prescription = prescriptionMapper.updatePrescriptionInfo(prescription,dto);
            prescription = prescriptionService.updatePrescription(prescription);
            return "redirect:/doctor/my-prescriptions";
        }catch (Exception e){
            System.out.println("Exception form Doctor Thymeleaf update prescription = "+e.getMessage());
            return "redirect:/doctor/dashboard?message="+e.getMessage();
        }
    }
    @GetMapping("/prescription/delete/{prescriptionId}")
    public String deletePrescription(@PathVariable("prescriptionId") Long prescriptionId, Model model,HttpServletRequest request){
        try {
            String jwt = jwtUtils.getJwtFromCookies(request);
            if(jwt==null) return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
            Long userId = jwtUtils.extractUserId(jwt);
            MyUser doctor = userService.findUserById(userId);
            if(doctor != null){
                System.out.println("delete prescription id get delete = "+prescriptionId);
                prescriptionService.deletePrescriptionById(prescriptionId);
                return "redirect:/doctor/my-prescriptions?message=Prescription deleted successfully";
            }
            else return "redirect:/user-logout?message=Server error, Prescription not delete";
        }catch (Exception e){
            System.out.println("Exception form Doctor Thymeleaf = "+e.getMessage());
            return "redirect:/user-logout?message=Server error, Prescription not delete";
        }
    }
    @GetMapping("/write-prescription") //-------------------------------- write prescription------------------
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
            System.out.println("Exception form Doctor Thymeleaf write prescription post= "+e.getMessage());
            return "redirect:/doctor/write-prescription?message="+e.getMessage();
        }
    }
    @GetMapping("/prescription-report")
    public String prescriptionReport(Model model,HttpServletRequest request){
        try {
            String token = jwtUtils.getJwtFromCookies(request);
            if(token!=null) {
                Long userId = jwtUtils.extractUserId(token);
                MyUser doctor = userService.findUserById(userId);
                List<PrescriptionCountPerDayDto> countPerDayDto = prescriptionService.countPrescriptionsByDateForDoctor(doctor);
                model.addAttribute("myPrescriptionsReport",countPerDayDto);
                return "ReportDoctorPrescription";
            }
            else return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
        }catch (Exception e) {
            System.out.println("Exception from Doctor Dashboard = "+e.getMessage());
            return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
        }
    }
    @GetMapping("/get-prescription-list") //----------------------------- Get doctor All Prescriptions ----------------------
    public String getPrescriptionList(Model model,HttpServletRequest request){
        try {
            String token = jwtUtils.getJwtFromCookies(request);
            if(token!=null) {
                Long userId = jwtUtils.extractUserId(token);
                MyUser doctor = userService.findUserById(userId);
                List<Prescription> myAllPrescription = prescriptionService.findAllPrescriptionByDoctor(doctor);
                model.addAttribute("myAllPrescription",myAllPrescription);
                return "ShowPrescription";
            }
            else return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
        }catch (Exception e) {
            System.out.println("Exception from Doctor Dashboard = "+e.getMessage());
            return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
        }
    }

    @GetMapping("/consume-get-api")
    public String consumeGetApi(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        ExternalPageDto result = externalApiService.getPosts(page, size);

        model.addAttribute("externalApiData", result.data());
        model.addAttribute("currentPage", result.currentPage());
        model.addAttribute("totalPages", result.totalPages());
        model.addAttribute("size", size);

        int prevPage = page > 1 ? page - 1 : 1;
        int nextPage = page < result.totalPages() ? page + 1 : result.totalPages();

        model.addAttribute("nextPage", nextPage);
        model.addAttribute("prevPage", prevPage);

        return "ExternalApiDataTable";
    }


    @GetMapping("/edit-personal-information") //--------------------------- Edit Personal Information ----------------------
    public String editPersonalInformation(Model model,HttpServletRequest request){
        try {
            String token = jwtUtils.getJwtFromCookies(request);
            if(token!=null) {
                Long userId = jwtUtils.extractUserId(token);
                MyUser doctor = userService.findUserById(userId);
                DoctorRegistrationDto doctorRegistrationDto = doctorMapper.mapToDto(doctor);
                model.addAttribute("doctorDto",doctorRegistrationDto);
                return "EditDoctorInformation";
            }
            else return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
        }catch (Exception e){
            System.out.println("Exception form Doctor Thymeleaf = "+e.getMessage());
            return "redirect:/user-logout?message=Server error, Prescription not delete";
        }
    }
    @PostMapping("/edit-personal-information")
    public String editPersonalInformationPost(@ModelAttribute("DoctorRegistrationDto") DoctorRegistrationDto dto, Model model,HttpServletRequest request){
        try {
            String validationResult = validation.doctorEditDataValidation(dto);
            if(!validationResult.equals("valid"))
                return "redirect:/doctor/edit-personal-information?message="+validationResult;

            String token = jwtUtils.getJwtFromCookies(request);
            if(token!=null) {
                Long userId = jwtUtils.extractUserId(token);
                MyUser doctor = userService.findUserById(userId);
                doctor = doctorMapper.mapToUpdateEntity(doctor,dto);
                doctor = userService.updateUser(doctor);
                if(doctor != null) {
                    return "redirect:/doctor/dashboard?message=Doctor registration successful";
                }
                else return "redirect:/doctor/edit-personal-information?message=Server error, Doctor not save";
            }
            else return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
        }catch (Exception e){
            System.out.println("Exception form Doctor Thymeleaf = "+e.getMessage());
            return "redirect:/user-logout?message=Server error, Not update";
        }
    }
    @GetMapping("/see-all-patient") // ---------------------------------- see all patient----------------
    public String seeAllPatient(Model model, HttpServletRequest request){
        try {
            String jwt = jwtUtils.getJwtFromCookies(request);
            if(jwt==null) return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
            if(!dataValidation.validUserRole("DOCTOR",jwt)) return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
            List<MyUser> allPatient = userRepository.findUsersByRoleName("PATIENT");
            model.addAttribute("allUsers",allPatient);
            return "PrescriptionForSystemPatient";
        }catch (Exception e){
            System.out.println("Exception form see all user = "+e.getMessage());
            return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
        }
    }
    @GetMapping("/write-prescription-for-system-patient/userID/{patientId}")
    public String createPrescriptionForSystemPatient(@PathVariable("patientId") Long patientId, Model model, HttpServletRequest request){
        try {
            MyUser patientUser = userService.findUserById(patientId);
            PrescriptionDto dto = new PrescriptionDto();
            dto.setPrescriptionDate(LocalDate.now());
            dto.setPatientName(patientUser.getName());
            dto.setPatientAge(Period.between(patientUser.getBirthday(), LocalDate.now()).getYears());
            dto.setPatientGender(patientUser.getGender());
            model.addAttribute("PrescriptionDto",dto);
            return "PrescriptionFromForSystemPatient";
        }catch (Exception e){
            System.out.println("Exception form Doctor Thymeleaf = "+e.getMessage());
            return "redirect:/user-logout?message=Server error, Prescription not delete";
        }
    }
}
