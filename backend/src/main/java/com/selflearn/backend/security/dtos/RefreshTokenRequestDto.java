package com.selflearn.backend.security.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RefreshTokenRequestDto(@NotNull UUID refreshToken) {
}
