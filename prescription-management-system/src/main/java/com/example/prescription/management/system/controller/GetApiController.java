package com.example.prescription.management.system.controller;

import com.example.prescription.management.system.jwt.JwtUtils;
import com.example.prescription.management.system.model.dto.ExternalDataDto;
import com.example.prescription.management.system.model.dto.PrescriptionDto;
import com.example.prescription.management.system.model.entity.MyUser;
import com.example.prescription.management.system.model.mapper.PrescriptionMapper;
import com.example.prescription.management.system.repository.PrescriptionRepository;
import com.example.prescription.management.system.service.ExternalApiService;
import com.example.prescription.management.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/GET")
@RequiredArgsConstructor
public class GetApiController {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final PrescriptionMapper prescriptionMapper;
    private final ExternalApiService externalApiService;
    private final PrescriptionRepository prescriptionRepository;

    @GetMapping("/API/v1/prescription")
    public ResponseEntity<?> getPrescription(@RequestParam("jwt token") String jwt) {
        try {
            if(jwtUtils.isTokenExpired(jwt)) return ResponseEntity.badRequest().body(Map.of("message", "Token expired"));
            Long doctorId = jwtUtils.extractUserId(jwt);
            MyUser doctor = userService.findUserById(doctorId);
            if(doctor == null) return ResponseEntity.badRequest().body(Map.of("message", "User not found"));
            List<PrescriptionDto> prescriptions = prescriptionRepository.findAllByDoctorId(doctorId).stream().map(prescriptionMapper::mapToDto).toList();
            //List<PrescriptionDto> prescriptions = doctor.getPrescriptions().stream().map(prescriptionMapper::mapToDto).toList();
            return ResponseEntity.ok(prescriptions);
        }catch (Exception e){
            System.out.println("Exception form get api controller = "+e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/api/v1/consume-REST-API")
    public ResponseEntity<?> getExternalApiCall() {
        try {
            List<ExternalDataDto> posts = externalApiService.getAllPosts();
            return ResponseEntity.ok(posts);
        }catch (Exception e){
            System.out.println("Exception form get api controller = "+e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
