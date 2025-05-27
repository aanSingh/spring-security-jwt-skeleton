package com.ecommerce.springboot_ecommerce_api.controller;

import com.ecommerce.springboot_ecommerce_api.dto.LogInRequest;
import com.ecommerce.springboot_ecommerce_api.dto.LogInResponse;
import com.ecommerce.springboot_ecommerce_api.dto.SignUpRequest;
import com.ecommerce.springboot_ecommerce_api.entity.User;
import com.ecommerce.springboot_ecommerce_api.service.AuthenticationService;
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
}
