package com.ecommerce.springboot_ecommerce_api.dto;

import lombok.Data;

@Data
public class LogInResponse {
    private String token;
    private String refreshToken;
}
