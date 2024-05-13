package com.selflearn.backend.gitExchange;

import com.selflearn.backend.gitExchange.services.GitExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SyncTask {

    private final GitExchangeService gitExchangeService;

    @Scheduled(cron = "0 0 * * *")
    void syncDataToGithubEveryday() {
        // sync data to github
        this.gitExchangeService.syncGithubWithDatabase();
    }
}
