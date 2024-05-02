package com.selflearn.backend.security;

import com.selflearn.backend.security.dtos.JwtResponse;
import com.selflearn.backend.security.dtos.LoginDto;
import com.selflearn.backend.security.dtos.SignupDto;
import com.selflearn.backend.security.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;
    @PostMapping("/login")
    public JwtResponse login(@Valid @RequestBody LoginDto loginDto) {
        return authService.validateUser(loginDto);
    }

    @PostMapping("/signup")
    public void signup(@Valid @RequestBody SignupDto signupDto) {
        authService.registerUser(signupDto);
    }
}
