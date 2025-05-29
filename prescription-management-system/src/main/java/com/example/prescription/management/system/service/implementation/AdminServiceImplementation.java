package com.example.prescription.management.system.service.implementation;

import com.example.prescription.management.system.model.entity.MyUser;
import com.example.prescription.management.system.model.entity.Role;
import com.example.prescription.management.system.repository.UserRepository;
import com.example.prescription.management.system.service.AdminService;
import com.example.prescription.management.system.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImplementation implements AdminService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Override
    public MyUser setUserNewRole(MyUser user,Role role) {
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    @Override
    public Role addNewRole(String roleName) {
        try {
            Role role = new Role();
            role.setName(roleName);
            return roleService.addRole(role);
        }catch (Exception e){
            System.out.println("Exception for adminServiceImplementation.addNewRole = "+e.getMessage());
            return null;
        }
    }
}
