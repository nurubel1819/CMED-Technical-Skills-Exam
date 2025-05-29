package com.example.prescription.management.system.service;

import com.example.prescription.management.system.model.entity.Role;

public interface RoleService {
    Role addRole(Role role);
    Role findRoleByName(String name);
}
