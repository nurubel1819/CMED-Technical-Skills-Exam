package com.example.prescription.management.system.thymeleaf;

import com.example.prescription.management.system.helper.DataValidation;
import com.example.prescription.management.system.jwt.JwtUtils;
import com.example.prescription.management.system.model.entity.MyUser;
import com.example.prescription.management.system.model.entity.Prescription;
import com.example.prescription.management.system.repository.PrescriptionRepository;
import com.example.prescription.management.system.repository.UserRepository;
import com.example.prescription.management.system.service.RoleService;
import com.example.prescription.management.system.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminThymeleaf {

    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final DataValidation validation;
    private final UserRepository userRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final RoleService roleService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model, HttpServletRequest request){
        String jwt = jwtUtils.getJwtFromCookies(request);
        if(jwt==null) return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
        Long userId = jwtUtils.extractUserId(jwt);
        String adminName = userService.findUserById(userId).getName();
        model.addAttribute("adminName",adminName);
        return "AdminDashboard";
    }
    @GetMapping("/see-all-user")
    public String seeAllUser(Model model, HttpServletRequest request){
        try {
            String jwt = jwtUtils.getJwtFromCookies(request);
            if(jwt==null) return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
            if(!validation.validUserRole("ADMIN",jwt)) return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
            List<MyUser> allUsers = userRepository.findAll();
            model.addAttribute("allUsers",allUsers);
            return "ShowUser";
        }catch (Exception e){
            System.out.println("Exception form see all user = "+e.getMessage());
            return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
        }
    }
    @GetMapping("/see-all-prescription")
    public String seeAllPrescription(Model model, HttpServletRequest request){
        try {
            String jwt = jwtUtils.getJwtFromCookies(request);
            if(jwt==null) return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
            if(!validation.validUserRole("ADMIN",jwt)) return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
            List<Prescription> allPrescription = prescriptionRepository.findAll();
            model.addAttribute("myAllPrescription",allPrescription);
            return "ShowPrescription";
        }catch (Exception e){
            System.out.println("Exception form see all user = "+e.getMessage());
            return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
        }
    }
    @GetMapping("/delete-user/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId, HttpServletRequest request){
        try {
            String jwt = jwtUtils.getJwtFromCookies(request);
            if(jwt==null) return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
            if(!validation.validUserRole("ADMIN",jwt)) return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
            userService.deleteUserById(userId);
            return "redirect:/admin/see-all-user?message=User deleted successfully";
        }catch (Exception e){
            System.out.println("Exception form see all user = "+e.getMessage());
            return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
        }
    }
    /*@GetMapping("/add-new-role")
    public String addNewRole(Model model, HttpServletRequest request){
        try {
            String jwt = jwtUtils.getJwtFromCookies(request);
            if(jwt==null) return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
            if(!validation.validUserRole("ADMIN",jwt)) return "redirect:/user-logout?message=User not found. Please check your phone and try again.";
            List<String> allSystemRole = roleService.getAllSystemRole();
            model.addAttribute("allSystemRole",allSystemRole);
            return "AddNewRole";
        }catch (Exception e){}
    }*/
    /*@GetMapping("/set-new-role/{userId}")
    public String setNewRole(@PathVariable("userId") Long userId){

    }*/
}
