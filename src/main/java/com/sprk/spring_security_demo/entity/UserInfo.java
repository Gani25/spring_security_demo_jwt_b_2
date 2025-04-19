package com.sprk.spring_security_demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Data
@Entity
@ToString
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    private String username;

    private String password;

    private String email;

    // ROLES
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_info_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleModel> roles = new HashSet<>();

}
