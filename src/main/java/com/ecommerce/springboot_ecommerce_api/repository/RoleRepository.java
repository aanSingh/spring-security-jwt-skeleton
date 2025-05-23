package com.ecommerce.springboot_ecommerce_api.repository;

import com.ecommerce.springboot_ecommerce_api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {}
