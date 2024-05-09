package com.selflearn.backend.gitExchange.daos;

import com.selflearn.backend.gitExchange.models.Commit;
import com.selflearn.backend.gitExchange.models.GitRepoTrees;
import com.selflearn.backend.gitExchange.models.GitTreeNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class GitExchangeDaoImpl implements GitExchangeDao {
    @Value("${selflearn.git.owner}")
    private String gitOwner;

    @Value("${selflearn.git.repo}")
    private String gitRepo;

    @Value("${selflearn.git.api.host}")
    private String gitApiHost;

    @Value("${selflearn.git.token}")
    private String gitToken;

    @Value("${selflearn.git.baseDir}")
    private String baseDir;

    @Value("${selflearn.git.observeBranch}")
    private String observeBranch;

    private final RestClient restClient;

    @Override
    public GitRepoTrees getRepoTrees(String sha) {
        return restClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .host(gitApiHost)
                                .scheme("https")
                                .path("/repos/{owner}/{repo}/git/trees/{sha}")
                                .queryParam("recursive", "0")
                                .build(gitOwner, gitRepo, sha))
                .header("Authorization", "Bearer " + gitToken)
                .retrieve()
                .toEntity(GitRepoTrees.class).getBody();
    }

    @Override
    public String getLatestSampleDirSha() {
        Commit commit = this.restClient.get().uri(uriBuilder -> uriBuilder
                        .host(gitApiHost)
                        .scheme("https")
                        .path("/repos/{owner}/{repo}/commits/{branch}")
                        .build(gitOwner, gitRepo, observeBranch))
                .header("Authorization", "Bearer " + gitToken)
                .retrieve().toEntity(Commit.class).getBody();

        if(commit == null){
            throw new RuntimeException("Cannot get latest commit sha");
        }
        String commitSha = commit.getSha();

        GitTreeNode gitTreeNode = this.getRepoTrees(commitSha).getTree().stream()
                .filter(tree -> tree.getPath().equals(baseDir))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cannot find baseDir in latest commit"));
        return gitTreeNode.getSha();
    }
}
