package com.example.prescription.management.system.repository;


import com.example.prescription.management.system.model.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByPhone(String phone);
    Optional<MyUser> findByEmail(String email);
    @Query("SELECT u FROM MyUser u JOIN u.roles r WHERE r.name = :roleName")
    List<MyUser> findUsersByRoleName(@Param("roleName") String roleName);

}
