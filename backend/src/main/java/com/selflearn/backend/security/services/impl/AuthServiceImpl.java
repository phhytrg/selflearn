package com.selflearn.backend.security.services.impl;

import com.selflearn.backend.security.RefreshTokenRepository;
import com.selflearn.backend.security.models.UserDetailsImpl;
import com.selflearn.backend.security.dtos.JwtResponseDto;
import com.selflearn.backend.security.dtos.LoginRequestDto;
import com.selflearn.backend.security.dtos.SignupRequestDto;
import com.selflearn.backend.security.jwt.JwtUtils;
import com.selflearn.backend.security.models.RefreshToken;
import com.selflearn.backend.security.services.AuthService;
import com.selflearn.backend.user.models.User;
import com.selflearn.backend.user.models.UserRole;
import com.selflearn.backend.user.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {

    @Value("${selflearn.app.jwtRefreshExpirationMs}")
    private long refreshTokenExpirationMs;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public JwtResponseDto validateUser(LoginRequestDto loginDto) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password())
            );
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid password or username");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = generateAccessToken(authentication);
        User user = userService.getReferenceById(((UserDetailsImpl) authentication.getPrincipal()).getId());
        UUID refreshToken = generateRefreshToken(user);
        return new JwtResponseDto(accessToken, refreshToken.toString());
    }

    @Override
    public void registerUser(SignupRequestDto signupDto) {
        // Register user
        User user = User.builder()
                .email(signupDto.email())
                .password(passwordEncoder.encode(signupDto.password()))
                .roles(new HashSet<>(List.of(UserRole.READER)))
                .build();
        userService.saveUser(user);
    }

    @Override
    public JwtResponseDto validateRefreshToken(UUID refreshToken) {
        RefreshToken token = refreshTokenRepository.findById(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh Token"));
        if (token.getExpiredAt() < new Date().getTime()) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh Token has expired");
        }
        User user = token.getUser();
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new JwtResponseDto(generateAccessToken(authentication), null);
    }

    @Override
    public void logout(UUID refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
    }

    @Override
    public void removeExpiredToken() {
        List<RefreshToken> expiredTokens = refreshTokenRepository
                .deleteRefreshTokenByExpiredAtBefore(new Date().getTime());
        log.info("Delete {} expired refresh tokens", expiredTokens.size());
    }

    @Override
    public UserDetails retrieveCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails;
    }

    private String generateAccessToken(Authentication authentication) {
        return jwtUtils.generateJwtToken(authentication);
    }

    private UUID generateRefreshToken(User user) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .expiredAt(new Date().getTime() + refreshTokenExpirationMs)
                .build();
        refreshTokenRepository.save(refreshToken);
        return refreshToken.getId();
    }
}
