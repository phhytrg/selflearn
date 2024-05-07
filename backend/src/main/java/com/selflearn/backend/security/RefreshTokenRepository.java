package com.selflearn.backend.security;

import com.selflearn.backend.security.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    List<RefreshToken> deleteRefreshTokenByExpiredAtBefore(Long tine);
}
