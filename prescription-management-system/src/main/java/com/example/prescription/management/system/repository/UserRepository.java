package com.example.prescription.management.system.repository;


import com.example.prescription.management.system.model.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByPhone(String phone);
}
