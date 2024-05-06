package com.selflearn.backend.security.dtos;

public record JwtResponseDto(String accessToken, String refreshToken) {
}
