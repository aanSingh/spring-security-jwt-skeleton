package com.ecommerce.springboot_ecommerce_api.service.imp;

import com.ecommerce.springboot_ecommerce_api.dto.SignUpRequest;
import com.ecommerce.springboot_ecommerce_api.entity.Role;
import com.ecommerce.springboot_ecommerce_api.entity.RoleType;
import com.ecommerce.springboot_ecommerce_api.entity.User;
import com.ecommerce.springboot_ecommerce_api.repository.RoleRepository;
import com.ecommerce.springboot_ecommerce_api.repository.UserRepository;
import com.ecommerce.springboot_ecommerce_api.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public User signUp(SignUpRequest signUpRequest) {
    User user = new User();
    user.setEmail(signUpRequest.getEmail());
    user.setFirstName(signUpRequest.getFirstName());
    user.setLastName(signUpRequest.getLastName());
    user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

    Role role =
        roleRepository
            .findByName(RoleType.ROLE_CUSTOMER)
            .orElseThrow(() -> new RuntimeException("Role not found"));
    user.setRole(role);

    return userRepository.save(user);
  }
}
