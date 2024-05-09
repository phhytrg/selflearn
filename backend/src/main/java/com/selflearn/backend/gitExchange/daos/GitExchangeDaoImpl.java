package com.selflearn.backend.gitExchange.daos;

import com.selflearn.backend.gitExchange.dtos.CreateBlobRequest;
import com.selflearn.backend.gitExchange.dtos.CreateBlobResponse;
import com.selflearn.backend.gitExchange.dtos.CreateCommitRequest;
import com.selflearn.backend.gitExchange.dtos.CreateCommitResponse;
import com.selflearn.backend.gitExchange.dtos.CreateTreeRequest;
import com.selflearn.backend.gitExchange.dtos.CreateTreeResponse;
import com.selflearn.backend.gitExchange.dtos.GitBaseResponse;
import com.selflearn.backend.gitExchange.dtos.GitRepoTrees;
import com.selflearn.backend.gitExchange.dtos.GitTreeNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

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
    public String getLatestSha() {
        GitBaseResponse commit = this.restClient.get().uri(uriBuilder -> uriBuilder
                        .host(gitApiHost)
                        .scheme("https")
                        .path("/repos/{owner}/{repo}/commits/{branch}")
                        .build(gitOwner, gitRepo, observeBranch))
                .header("Authorization", "Bearer " + gitToken)
                .retrieve().toEntity(GitBaseResponse.class).getBody();

        if (commit == null) {
            throw new RuntimeException("Cannot get latest commit sha");
        }
        return commit.getSha();
    }

    @Override
    public String getLatestSampleDirSha() {
        GitTreeNode gitTreeNode = this.getRepoTrees(this.getLatestSha()).getTree().stream()
                .filter(tree -> tree.getPath().equals(baseDir))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cannot find baseDir in latest commit"));
        return gitTreeNode.getSha();
    }

    @Override
    public CreateBlobResponse createBlob(String content) {
        return this.restClient.post()
                .uri(uriBuilder -> uriBuilder
                        .host(gitApiHost)
                        .scheme("https")
                        .path("/repos/{owner}/{repo}/git/blobs")
                        .build(gitOwner, gitRepo))
                .header("Authorization", "Bearer " + gitToken)
                .body(CreateBlobRequest.builder()
                        .content(content)
                        .encoding(CreateBlobRequest.EncodingType.BASE64)
                        .build())
                .retrieve()
                .toEntity(CreateBlobResponse.class).getBody();
    }

    @Override
    public CreateTreeResponse createTree(String baseTreeSha, List<String> newTreeSha, String path, String mode, String type) {
        return this.restClient.post().uri(
                        uriBuilder -> uriBuilder
                                .host(gitApiHost)
                                .scheme("https")
                                .path("/repos/{owner}/{repo}/git/trees")
                                .build(gitOwner, gitRepo))
                .header("Authorization", "Bearer " + gitToken)
                .body(CreateTreeRequest.builder()
                        .base_tree(baseTreeSha)
                        .tree(new CreateTreeRequest.TreeNodeRequest[]{
                                newTreeSha.stream().map(sha -> CreateTreeRequest.TreeNodeRequest.builder()
                                                .path(path)
                                                .mode(mode)
                                                .type(type)
                                                .sha(sha)
                                                .build())
                                        .findFirst().orElseThrow(() -> new RuntimeException("Cannot create tree"))
                        })
                        .build())
                .retrieve().toEntity(CreateTreeResponse.class).getBody();
    }

    @Override
    public CreateCommitResponse createCommit(String treeSha, String parentCommitSha, String commitMessage) {
        return this.restClient.post().uri(
                        uriBuilder -> uriBuilder.host(gitApiHost)
                                .scheme("https")
                                .path("/repos/{owner}/{repo}/git/commits")
                                .build(gitOwner, gitRepo))
                .header("Authorization", "Bearer " + gitToken)
                .body(CreateCommitRequest.builder()
                        .message(commitMessage)
                        .tree(treeSha)
                        .parents(new String[]{parentCommitSha})
                        .build())
                .retrieve().toEntity(CreateCommitResponse.class).getBody();
    }

    @Override
    public CreateCommitResponse updateReference(CreateCommitResponse createCommitResponse) {
        this.restClient.patch().uri(uriBuilder -> uriBuilder
                        .host(gitApiHost)
                        .scheme("https")
                        .path("/repos/{owner}/{repo}/git/refs/heads/{branch}")
                        .build(gitOwner, gitRepo, observeBranch))
                .header("Authorization", "Bearer " + gitToken)
                .body("{\"sha\":\"" + createCommitResponse.getSha() + "\"}")
                .retrieve().toEntity(Void.class);
        return createCommitResponse;
    }
}
