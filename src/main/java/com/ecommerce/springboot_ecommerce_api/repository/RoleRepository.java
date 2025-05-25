package com.ecommerce.springboot_ecommerce_api.repository;

import com.ecommerce.springboot_ecommerce_api.entity.Role;
import com.ecommerce.springboot_ecommerce_api.entity.RoleType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(RoleType name);
}
