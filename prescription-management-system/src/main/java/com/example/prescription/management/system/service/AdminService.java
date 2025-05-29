package com.example.prescription.management.system.service;

import com.example.prescription.management.system.model.entity.MyUser;
import com.example.prescription.management.system.model.entity.Role;

public interface AdminService {
    MyUser setUserNewRole(MyUser user, Role role);
    Role addNewRole(String roleName);
}
