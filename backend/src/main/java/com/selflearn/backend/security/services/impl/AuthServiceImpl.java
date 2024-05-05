package com.selflearn.backend.security.services.impl;

import com.selflearn.backend.security.RefreshTokenRepository;
import com.selflearn.backend.security.UserDetailsImpl;
import com.selflearn.backend.security.dtos.JwtResponse;
import com.selflearn.backend.security.dtos.LoginDto;
import com.selflearn.backend.security.dtos.SignupDto;
import com.selflearn.backend.security.jwt.JwtUtils;
import com.selflearn.backend.security.models.RefreshToken;
import com.selflearn.backend.security.services.AuthService;
import com.selflearn.backend.user.UserRepository;
import com.selflearn.backend.user.models.User;
import com.selflearn.backend.user.models.UserRole;
import com.selflearn.backend.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${selflearn.app.jwtRefreshExpirationMs}")
    private long refreshTokenExpirationMs;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public JwtResponse validateUser(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = generateAccessToken(authentication);
        User user = userService.getReferenceById(((UserDetailsImpl) authentication.getPrincipal()).getId());
        String refreshToken = generateRefreshToken(user);
        return new JwtResponse(accessToken, refreshToken);
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

    @Override
    public JwtResponse validateRefreshToken(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh Token"));
        if (token.getExpiredAt() < new Date().getTime()) {
            throw new RuntimeException("Refresh Token has expired");
        }
        User user = token.getUser();
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new JwtResponse(generateAccessToken(authentication), null);
    }

    private String generateAccessToken(Authentication authentication) {
        return jwtUtils.generateJwtToken(authentication);
    }

    private String generateRefreshToken(User user) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiredAt(new Date().getTime() + refreshTokenExpirationMs)
                .build();
        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }
}
