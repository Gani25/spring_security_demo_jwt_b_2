package com.sprk.spring_security_demo.repository;

import com.sprk.spring_security_demo.entity.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleModelRepository extends JpaRepository<RoleModel,Integer> {

    Optional<RoleModel> findByRole(String role);
}
