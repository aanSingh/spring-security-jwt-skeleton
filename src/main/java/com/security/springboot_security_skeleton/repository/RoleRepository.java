package com.security.springboot_security_skeleton.repository;

import com.security.springboot_security_skeleton.entity.Role;
import com.security.springboot_security_skeleton.entity.RoleType;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(RoleType name);
}
