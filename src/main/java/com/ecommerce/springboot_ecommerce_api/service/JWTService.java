package com.ecommerce.springboot_ecommerce_api.service;

import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {

  String generateToken(UserDetails user);

  String extractUsername(String token);

  boolean isTokenValid(String jwt, UserDetails userDetails);

  String generateRefreshToken(Map<String, Object> extraClaims, UserDetails user);
}
