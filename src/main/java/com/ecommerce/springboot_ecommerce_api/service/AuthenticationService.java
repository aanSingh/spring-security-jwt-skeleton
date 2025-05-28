package com.ecommerce.springboot_ecommerce_api.service;

import com.ecommerce.springboot_ecommerce_api.dto.LogInRequest;
import com.ecommerce.springboot_ecommerce_api.dto.LogInResponse;
import com.ecommerce.springboot_ecommerce_api.dto.RefreshTokenRequest;
import com.ecommerce.springboot_ecommerce_api.dto.SignUpRequest;
import com.ecommerce.springboot_ecommerce_api.entity.User;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);

    LogInResponse logIn(LogInRequest logInRequest);

    LogInResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
