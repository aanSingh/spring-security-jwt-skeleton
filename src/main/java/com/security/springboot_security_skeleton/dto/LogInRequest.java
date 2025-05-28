package com.security.springboot_security_skeleton.dto;

import lombok.Data;

@Data
public class LogInRequest {
  private String email;
  private String password;
}
