package com.selflearn.backend.security;

import com.selflearn.backend.security.dtos.JwtResponseDto;
import com.selflearn.backend.security.dtos.LoginRequestDto;
import com.selflearn.backend.security.dtos.RefreshTokenRequestDto;
import com.selflearn.backend.security.dtos.SignupRequestDto;
import com.selflearn.backend.security.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("${apiPrefix}/${apiVersion}/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public JwtResponseDto login(@Valid @RequestBody LoginRequestDto loginDto) {
        return authService.validateUser(loginDto);
    }

    @PostMapping("/signup")
    public void signup(@Valid @RequestBody SignupRequestDto signupDto) {
        authService.registerUser(signupDto);
    }

    @PostMapping("/refresh")
    public JwtResponseDto refresh(@RequestBody RefreshTokenRequestDto refreshTokenDto) {
        return authService.validateRefreshToken(refreshTokenDto.refreshToken());
    }

    @PostMapping("/logout")
    public void logout(@RequestBody RefreshTokenRequestDto refreshTokenDto) {
        authService.logout(refreshTokenDto.refreshToken());
    }
}
