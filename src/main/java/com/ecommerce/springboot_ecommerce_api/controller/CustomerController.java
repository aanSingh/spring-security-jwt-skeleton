package com.ecommerce.springboot_ecommerce_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
  @GetMapping
  public ResponseEntity<String> checkCustomer() {
    return ResponseEntity.ok("Hello from Customer");
  }
}
