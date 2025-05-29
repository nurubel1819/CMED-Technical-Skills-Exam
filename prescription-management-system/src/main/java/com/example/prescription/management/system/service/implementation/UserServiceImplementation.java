package com.example.prescription.management.system.service.implementation;

import com.example.prescription.management.system.model.entity.MyUser;
import com.example.prescription.management.system.model.entity.Role;
import com.example.prescription.management.system.repository.UserRepository;
import com.example.prescription.management.system.service.RoleService;
import com.example.prescription.management.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Override
    public MyUser saveUser(MyUser user) {
        if(userRepository.findByPhone(user.getPhone()).isPresent()){
            return null;
        }
        try {
            Role role = roleService.findRoleByName("USER");
            if(role==null){
                role = new Role();
                role.setName("USER");
                role = roleService.addRole(role);
            }
            user.getRoles().add(role);
            return userRepository.save(user);
        }catch (Exception e) {
            return null;
        }
    }
    @Override
    public MyUser findUserByPhone(String phone) {
        return userRepository.findByPhone(phone).orElse(null);
    }
    @Override
    public MyUser findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
