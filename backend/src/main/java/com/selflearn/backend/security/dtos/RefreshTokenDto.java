package com.selflearn.backend.security.dtos;

import jakarta.validation.constraints.NotNull;

public record RefreshTokenDto (@NotNull String refreshToken) {
}
