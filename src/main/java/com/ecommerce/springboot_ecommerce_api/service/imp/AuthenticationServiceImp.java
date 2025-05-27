package com.ecommerce.springboot_ecommerce_api.service.imp;

import com.ecommerce.springboot_ecommerce_api.dto.LogInRequest;
import com.ecommerce.springboot_ecommerce_api.dto.LogInResponse;
import com.ecommerce.springboot_ecommerce_api.dto.SignUpRequest;
import com.ecommerce.springboot_ecommerce_api.entity.CustomUserDetails;
import com.ecommerce.springboot_ecommerce_api.entity.Role;
import com.ecommerce.springboot_ecommerce_api.entity.RoleType;
import com.ecommerce.springboot_ecommerce_api.entity.User;
import com.ecommerce.springboot_ecommerce_api.repository.RoleRepository;
import com.ecommerce.springboot_ecommerce_api.repository.UserRepository;
import com.ecommerce.springboot_ecommerce_api.service.AuthenticationService;
import com.ecommerce.springboot_ecommerce_api.service.JWTService;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JWTService jwtService;

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

  @Override
  public LogInResponse logIn(LogInRequest logInRequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            logInRequest.getEmail(), logInRequest.getPassword()));

    User user =
        userRepository
            .findByEmail(logInRequest.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

    CustomUserDetails customUserDetails = new CustomUserDetails(user);
    String token = jwtService.generateToken(customUserDetails);
    String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), customUserDetails);

    LogInResponse logInResponse = new LogInResponse();
    logInResponse.setToken(token);
    logInResponse.setRefreshToken(refreshToken);

    return logInResponse;
  }
}
