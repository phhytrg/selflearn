package com.selflearn.backend.security.dtos;

import jakarta.validation.constraints.NotNull;

public record RefreshTokenRequestDto(@NotNull String refreshToken) {
}
