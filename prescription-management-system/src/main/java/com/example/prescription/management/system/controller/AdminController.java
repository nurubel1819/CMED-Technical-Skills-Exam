package com.example.prescription.management.system.controller;

import com.example.prescription.management.system.model.dto.RoleDto;
import com.example.prescription.management.system.model.entity.MyUser;
import com.example.prescription.management.system.model.entity.Role;
import com.example.prescription.management.system.service.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final RoleService roleService;

    @PostMapping("/add-new-role")
    public ResponseEntity<?> addNewRole(@RequestBody RoleDto dto) {
        Role role = roleService.addRole(dto.getName());
        if(role != null) {return ResponseEntity.ok(role);}
        else {return ResponseEntity.badRequest().body(Map.of("message", "Role not added"));}
    }
    @PostMapping("/find-by-role-name")
    public ResponseEntity<?> findRoleByName(@RequestBody RoleDto dto) {
        Role role = roleService.findRoleByName(dto.getName());
        if(role != null) {
            Set<Long> userIds = role.getUsers().stream().map(MyUser::getId).collect(Collectors.toSet());
            dto.setUsers(userIds);
            return ResponseEntity.ok(dto);
        }
        else {return ResponseEntity.badRequest().body(Map.of("message", "Role not found"));}
    }
}
