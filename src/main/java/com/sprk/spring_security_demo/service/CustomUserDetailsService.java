package com.sprk.spring_security_demo.service;

import com.sprk.spring_security_demo.configuration.CustomUserDetails;
import com.sprk.spring_security_demo.entity.UserInfo;
import com.sprk.spring_security_demo.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            System.out.println("Trying To Find: "+username);
            Optional<UserInfo> optionalUserInfo = userInfoRepository.findByUsername(username);
//        System.out.println("User Obj: " + optionalUserInfo.orElse(null));
            UserInfo userInfo = optionalUserInfo.orElse(null);
            System.out.println("User Obj: " + userInfo);
            if (userInfo == null) {
                throw new UsernameNotFoundException("User not found");
            } else {

                return new CustomUserDetails(userInfo);
            }
        }catch (Exception e){
            System.out.println("ERROR " + e.getMessage());
            e.printStackTrace();
            throw new UsernameNotFoundException("User not found");
        }
    }
}
