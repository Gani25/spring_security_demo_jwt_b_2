package com.sprk.spring_security_demo.configuration;

import com.sprk.spring_security_demo.entity.RoleModel;
import com.sprk.spring_security_demo.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    // FIELDS
    private Set<GrantedAuthority> authorities;
    private String username;
    private String password;

    // constructor
    public CustomUserDetails(UserInfo userInfo) {
        this.username = userInfo.getUsername();
        this.password = userInfo.getPassword();
        if(userInfo.getRoles() != null) {
            this.authorities = userInfo.getRoles().stream()
                    .map(role -> {
                        System.out.println("Role: "+role.getRole());
                        return new SimpleGrantedAuthority(role.getRole());
                    })
                    .collect(Collectors.toSet());
        }else {
            System.out.println("Warning: roles were null");
            this.authorities = new HashSet<>();
        }

        /*
        Set<RoleModel> roles = userInfo.getRoles();
        List<GrantedAuthority> authorities = new ArrayList<>(); // Empty
        for(RoleModel role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
            // Filling with roles
        }
        // CLass Field
        this.authorities = authorities;*/
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
