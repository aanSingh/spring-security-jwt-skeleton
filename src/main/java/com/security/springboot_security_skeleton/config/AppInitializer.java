package com.security.springboot_security_skeleton.config;

import com.security.springboot_security_skeleton.entity.Role;
import com.security.springboot_security_skeleton.entity.RoleType;
import com.security.springboot_security_skeleton.entity.User;
import com.security.springboot_security_skeleton.repository.RoleRepository;
import com.security.springboot_security_skeleton.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer {

  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public AppInitializer(
      RoleRepository roleRepository,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder) {
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @PostConstruct
  public void init() {
    // Step 1: Create roles if not exist
    for (RoleType roleType : RoleType.values()) {
      roleRepository
          .findByName(roleType)
          .orElseGet(
              () -> {
                Role role = new Role();
                role.setName(roleType);
                return roleRepository.save(role);
              });
    }

    // Step 2: Create default admin user if not exist
    String adminEmail = "admin@ecommerce.com";
    if (userRepository.findByEmail(adminEmail).isEmpty()) {
      User admin = new User();
      admin.setEmail(adminEmail);
      admin.setFirstName("Admin");
      admin.setLastName("User");
      admin.setPassword(passwordEncoder.encode("admin123")); // change this in production

      Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN).orElseThrow();
      admin.setRole(adminRole);

      userRepository.save(admin);
    }
  }
}
