package com.selflearn.backend.security;

import com.selflearn.backend.security.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveExpiredTokenTask {

    private final AuthService authService;

    @Scheduled(cron = "0 0 0 * * *")
    public void removeExpiredToken() {
        authService.removeExpiredToken();
    }
}
