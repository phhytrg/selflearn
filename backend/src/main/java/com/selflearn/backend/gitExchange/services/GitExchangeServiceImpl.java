package com.selflearn.backend.gitExchange.services;

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

    private final RestClient restClient;

    @Override
    public RepoTrees getRepoTrees(String owner, String repo) {
        RepoTrees repoTreesResponseDto = restClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .host(gitApiHost)
                                .scheme("https")
                                .path("/repos/{owner}/{repo}/git/trees/{sha}")
                                .queryParam("recursive", "0")
                                .build(owner, repo, gitSampleDirSha))
                .retrieve()
                .toEntity(RepoTrees.class).getBody();
        return repoTreesResponseDto;
    }
}
