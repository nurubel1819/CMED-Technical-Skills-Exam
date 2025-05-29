package com.example.prescription.management.system.service;

import com.example.prescription.management.system.model.entity.MyUser;
import com.example.prescription.management.system.model.entity.Role;


public interface UserService {
    public MyUser saveUser(MyUser user);
    public MyUser findUserByPhone(String phone);
    public MyUser findUserById(Long id);
    public void deleteUserById(Long id);
}
