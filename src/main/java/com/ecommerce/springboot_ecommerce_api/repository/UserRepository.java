package com.ecommerce.springboot_ecommerce_api.repository;

import com.ecommerce.springboot_ecommerce_api.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
}
