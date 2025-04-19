package com.sprk.spring_security_demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Entity
@Data
@ToString
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    private String role;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    @ToString.Exclude               // <-- Prevent printing toString()
    @EqualsAndHashCode.Exclude     // <-- Prevent triggering equals() deep compare
    @JsonIgnore                    // <-- If you're using Jackson to serialize (e.g., REST)
    private Set<UserInfo> users;
}
