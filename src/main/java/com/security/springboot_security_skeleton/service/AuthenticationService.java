package com.security.springboot_security_skeleton.service;

import com.security.springboot_security_skeleton.dto.LogInRequest;
import com.security.springboot_security_skeleton.dto.LogInResponse;
import com.security.springboot_security_skeleton.dto.RefreshTokenRequest;
import com.security.springboot_security_skeleton.dto.SignUpRequest;
import com.security.springboot_security_skeleton.entity.User;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);

    LogInResponse logIn(LogInRequest logInRequest);

    LogInResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
