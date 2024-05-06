package com.selflearn.backend.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

public class RestClientConfig {
    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }
}
