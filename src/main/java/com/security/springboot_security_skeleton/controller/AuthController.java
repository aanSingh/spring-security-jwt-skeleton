package com.security.springboot_security_skeleton.controller;

import com.security.springboot_security_skeleton.dto.LogInRequest;
import com.security.springboot_security_skeleton.dto.LogInResponse;
import com.security.springboot_security_skeleton.dto.RefreshTokenRequest;
import com.security.springboot_security_skeleton.dto.SignUpRequest;
import com.security.springboot_security_skeleton.entity.User;
import com.security.springboot_security_skeleton.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthenticationService authenticationService;

  @RequestMapping("/signup")
  public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest) {
    return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
  }

  @RequestMapping("/login")
  public ResponseEntity<LogInResponse> login(@RequestBody LogInRequest logInRequest) {
    return ResponseEntity.ok(authenticationService.logIn(logInRequest));
  }

  @RequestMapping("/refresh")
  public ResponseEntity<LogInResponse> login(@RequestBody RefreshTokenRequest refreshTokenRequest) {
    return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
  }
}
