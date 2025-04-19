//package com.sprk.spring_security_demo.configuration;
//
//import com.sprk.spring_security_demo.entity.RoleModel;
//import com.sprk.spring_security_demo.repository.RoleModelRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataLoader implements ApplicationRunner {
//
//    @Autowired
//    private RoleModelRepository roleModelRepository;
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        if (roleModelRepository.findByRole("ROLE_USER").isEmpty()) {
//            RoleModel roleModel = new RoleModel();
//            roleModel.setRole("ROLE_USER");
//            roleModelRepository.save(roleModel);
//        }
//    }
//}
