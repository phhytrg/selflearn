package com.selflearn.backend.gitExchange;

import com.selflearn.backend.gitExchange.services.GitExchangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class SyncTask {

    private final GitExchangeService gitExchangeService;

//    @Scheduled(fixedDelay = 10000)
//    void syncDataToGithubEveryday() {
//        // sync data to github
//        this.gitExchangeService.syncGithubWithDatabase();
//        log.info("Synced github");
//    }
}
