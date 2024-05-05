package com.selflearn.backend.security.services;

import com.selflearn.backend.security.dtos.JwtResponse;
import com.selflearn.backend.security.dtos.LoginDto;
import com.selflearn.backend.security.dtos.SignupDto;

public interface AuthService {
    JwtResponse validateUser(LoginDto loginDto);
    void registerUser(SignupDto signupDto);
    JwtResponse validateRefreshToken(String refreshToken);
}
