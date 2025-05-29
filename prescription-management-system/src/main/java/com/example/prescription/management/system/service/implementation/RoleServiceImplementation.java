package com.example.prescription.management.system.service.implementation;

import com.example.prescription.management.system.model.entity.Role;
import com.example.prescription.management.system.repository.RoleRepository;
import com.example.prescription.management.system.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImplementation implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role addRole(Role role) {
        try {
            return roleRepository.save(role);
        }catch (Exception e) {
            return null;
        }
    }
    @Override
    public Role findRoleByName(String name) {
        System.out.println("Role name = "+name);
        return roleRepository.findByName(name).orElse(null);
    }
}
