package com.example.prescription.management.system.service.implementation;

import com.example.prescription.management.system.jwt.JwtUtils;
import com.example.prescription.management.system.model.dto.JwtAuthenticationResponseDto;
import com.example.prescription.management.system.model.dto.SignInRequestDto;
import com.example.prescription.management.system.model.entity.MyUser;
import com.example.prescription.management.system.model.entity.Role;
import com.example.prescription.management.system.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImplementation implements AuthenticationService {

    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public MyUser sinUp(MyUser user,String defaultRole){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        MyUser saveUser = userService.saveUser(user);
        if(saveUser == null) return null;
        saveUser = roleService.setUserRole(saveUser, defaultRole);
        if(saveUser.getId()==1L) // here make first user is admin
        {
            Role role = roleService.findRoleByName("ADMIN");
            if(role==null)
            {
                role = roleService.addRole("admin");
                saveUser = roleService.setUserRole(saveUser,role.getName());
            }
            else saveUser = roleService.setUserRole(saveUser,role.getName());
        }
        return saveUser;
    }
    @Override
    public JwtAuthenticationResponseDto signIn(SignInRequestDto signInRequestDto) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInRequestDto.getPhone(),signInRequestDto.getPassword()
                    ));

            var user = userService.findUserByPhone(signInRequestDto.getPhone());
            var token = jwtUtils.generateToken(user.getId(), user.getPhone(), List.of(user.getRoles().stream().map(Role::getName).toArray(String[]::new)));
            //var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);

            System.out.println("Token: "+token);
            JwtAuthenticationResponseDto jwtAuthenticationResponseDto = new JwtAuthenticationResponseDto();
            jwtAuthenticationResponseDto.setToken(token);
            return jwtAuthenticationResponseDto;
        }catch (Exception e){
            return null;
        }
    }
}
