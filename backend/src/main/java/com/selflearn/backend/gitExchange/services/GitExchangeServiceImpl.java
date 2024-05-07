package com.selflearn.backend.gitExchange.services;

import com.selflearn.backend.gitExchange.models.Content;
import com.selflearn.backend.gitExchange.models.RepoTrees;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class GitExchangeServiceImpl implements GitExchangeService {

    @Value("${selflearn.git.owner}")
    private String gitOwner;

    @Value("${selflearn.git.repo}")
    private String gitRepo;

    @Value("${selflearn.git.sample.sha}")
    private String gitSampleDirSha;

    @Value("${selflearn.git.api.host}")
    private String gitApiHost;

    @Value("${selflearn.git.token}")
    private String gitToken;

    private final RestClient restClient;

    @Override
    public RepoTrees getRepoTrees() {
        return restClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .host(gitApiHost)
                                .scheme("https")
                                .path("/repos/{owner}/{repo}/git/trees/{sha}")
                                .queryParam("recursive", "0")
                                .build(gitOwner, gitRepo, gitSampleDirSha))
                .header("Authorization", "Bearer " + gitToken)
                .retrieve()
                .toEntity(RepoTrees.class).getBody();
    }

    @Override
    public Content getContent(String path) {
        return restClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .host(gitApiHost)
                                .scheme("https")
                                .path("/repos/{owner}/{repo}/contents/{path}")
                                .build(gitOwner, gitRepo, path))
                .header("Authorization", "Bearer " + gitToken)
                .retrieve()
                .toEntity(Content.class).getBody();
    }
}
