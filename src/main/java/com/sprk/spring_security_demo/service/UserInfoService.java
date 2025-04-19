package com.sprk.spring_security_demo.service;

import com.sprk.spring_security_demo.entity.RoleModel;
import com.sprk.spring_security_demo.entity.UserInfo;
import com.sprk.spring_security_demo.repository.RoleModelRepository;
import com.sprk.spring_security_demo.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private RoleModelRepository roleModelRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserInfo saveUserInfo(UserInfo userInfo) {

        UserInfo dbUser = userInfoRepository.findByUsername(userInfo.getUsername()).orElse(null);
        if(dbUser == null){
            userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
            RoleModel roleModel = roleModelRepository.findByRole("ROLE_USER").orElse(null);

            userInfo.setRoles(Collections.singleton(roleModel));

            return userInfoRepository.save(userInfo);

        }
        else {
            System.out.println("Already EXISTS\n\n");
            return dbUser;
        }
    }
}
