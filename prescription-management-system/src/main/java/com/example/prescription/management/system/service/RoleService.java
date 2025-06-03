package com.example.prescription.management.system.service;

import com.example.prescription.management.system.model.entity.MyUser;
import com.example.prescription.management.system.model.entity.Role;

import java.util.List;

public interface RoleService {
    Role addRole(String roleName);
    Role findRoleByName(String name);
    MyUser setUserRole(MyUser user, String roleName);
    List<String> getAllSystemRole();
}
