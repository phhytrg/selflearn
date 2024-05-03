package com.selflearn.backend.security.services;

import com.selflearn.backend.security.dtos.JwtResponse;
import com.selflearn.backend.security.dtos.LoginDto;
import com.selflearn.backend.security.dtos.SignupDto;
import com.selflearn.backend.security.jwt.JwtUtils;
import com.selflearn.backend.user.models.User;
import com.selflearn.backend.user.models.UserRole;
import com.selflearn.backend.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtResponse validateUser(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        return new JwtResponse(jwt);
    }

    @Override
    public void registerUser(SignupDto signupDto) {
        // Register user
        User user = User.builder()
                .email(signupDto.email())
                .password(passwordEncoder.encode(signupDto.password()))
                .roles(new HashSet<>(List.of(UserRole.READER)))
                .build();
        userService.saveUser(user);
    }
}
