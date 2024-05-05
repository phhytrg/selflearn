package com.selflearn.backend.security.dtos;

public record JwtResponse(String accessToken, String refreshToken) {
}
