package com.ecommerce.springboot_ecommerce_api.dto;

import lombok.Data;

@Data
public class LogInRequest {
  private String email;
  private String password;
}
