package com.security.springboot_security_skeleton.service.imp;

import com.security.springboot_security_skeleton.dto.LogInRequest;
import com.security.springboot_security_skeleton.dto.LogInResponse;
import com.security.springboot_security_skeleton.dto.RefreshTokenRequest;
import com.security.springboot_security_skeleton.dto.SignUpRequest;
import com.security.springboot_security_skeleton.entity.CustomUserDetails;
import com.security.springboot_security_skeleton.entity.Role;
import com.security.springboot_security_skeleton.entity.RoleType;
import com.security.springboot_security_skeleton.entity.User;
import com.security.springboot_security_skeleton.repository.RoleRepository;
import com.security.springboot_security_skeleton.repository.UserRepository;
import com.security.springboot_security_skeleton.service.AuthenticationService;
import com.security.springboot_security_skeleton.service.JWTService;
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

  @Override
  public LogInResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
    String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());

    User user =
        userRepository
            .findByEmail(userEmail)
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

    if (jwtService.isTokenValid(refreshTokenRequest.getToken(), new CustomUserDetails(user))) {
      String token = jwtService.generateToken(new CustomUserDetails(user));

      LogInResponse logInResponse = new LogInResponse();
      logInResponse.setToken(token);
      logInResponse.setRefreshToken(refreshTokenRequest.getToken());
      return logInResponse;
    }
    return null;
  }
}
