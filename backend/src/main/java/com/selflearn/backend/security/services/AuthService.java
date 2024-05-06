package com.selflearn.backend.security.services;

import com.selflearn.backend.security.dtos.JwtResponseDto;
import com.selflearn.backend.security.dtos.LoginRequestDto;
import com.selflearn.backend.security.dtos.SignupRequestDto;

public interface AuthService {
    JwtResponseDto validateUser(LoginRequestDto loginDto);
    void registerUser(SignupRequestDto signupDto);
    JwtResponseDto validateRefreshToken(String refreshToken);
    void logout(String refreshToken);
    void removeExpiredToken();
}
