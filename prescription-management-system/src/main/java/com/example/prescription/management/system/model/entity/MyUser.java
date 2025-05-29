package com.example.prescription.management.system.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "my_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String phone;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "user_roles_map", // মাঝখানের টেবিলের নাম
            joinColumns = @JoinColumn(name = "user_id"), // বর্তমান entity এর কলাম
            inverseJoinColumns = @JoinColumn(name = "role_id") // অপর entity এর কলাম
    )
    private Set<Role> roles = new HashSet<>();
}
