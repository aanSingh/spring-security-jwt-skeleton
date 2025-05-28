package com.security.springboot_security_skeleton.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
  @GetMapping
  public ResponseEntity<String> checkAdmin() {
    return ResponseEntity.ok("Hello from Admin");
  }
}
