package com.example.prescription.management.system.service.implementation;

import com.example.prescription.management.system.model.entity.MyUser;
import com.example.prescription.management.system.model.entity.Role;
import com.example.prescription.management.system.repository.RoleRepository;
import com.example.prescription.management.system.repository.UserRepository;
import com.example.prescription.management.system.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImplementation implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public Role addRole(String roleName) {
        roleName = roleName.toUpperCase();

        if (roleRepository.findByName(roleName).isPresent()) return roleRepository.findByName(roleName).get();
        try {
            Role role = new Role();
            role.setName(roleName);
            return roleRepository.save(role);
        }catch (Exception e){
            System.out.println("Exception from RoleServiceImplementation.addRole = "+e.getMessage());
            return null;
        }
    }

    @Override
    public Role findRoleByName(String name) {
        System.out.println("Role name = "+name);
        return roleRepository.findByName(name).orElse(null);
    }

    @Override
    public MyUser setUserRole(MyUser user, String roleName) {
        Role role = findRoleByName(roleName);
        if(role == null) {
            role = addRole(roleName);
        }
        try {
            user.getRoles().add(role);
            return userRepository.save(user);
        }catch (Exception e) {
            System.out.println("Exception form RoleServiceImplementation.setUserRole = "+e.getMessage());
            return null;
        }
    }
}
