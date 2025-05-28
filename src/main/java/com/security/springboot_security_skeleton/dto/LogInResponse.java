package com.security.springboot_security_skeleton.dto;

import lombok.Data;

@Data
public class LogInResponse {
    private String token;
    private String refreshToken;
}
