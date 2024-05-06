package com.selflearn.backend.security.services;

import com.selflearn.backend.security.dtos.JwtResponseDto;
import com.selflearn.backend.security.dtos.LoginRequestDto;
import com.selflearn.backend.security.dtos.SignupRequestDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface AuthService {
    JwtResponseDto validateUser(LoginRequestDto loginDto);
    void registerUser(SignupRequestDto signupDto);
    JwtResponseDto validateRefreshToken(UUID refreshToken);
    void logout(UUID refreshToken);
    void removeExpiredToken();
    UserDetails retrieveCurrentUser();
}
