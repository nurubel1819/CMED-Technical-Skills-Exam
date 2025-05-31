package com.example.prescription.management.system.service;

import com.example.prescription.management.system.model.entity.MyUser;
import com.example.prescription.management.system.model.entity.Role;

public interface RoleService {
    Role addRole(String roleName);
    Role findRoleByName(String name);
    MyUser setUserRole(MyUser user, String roleName);
}
