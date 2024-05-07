package com.selflearn.backend.gitExchange.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.selflearn.backend.gitExchange.dtos.ContentDto;
import com.selflearn.backend.gitExchange.dtos.RepoTreesDto;
import com.selflearn.backend.gitExchange.dtos.TreeNodeDto;
import com.selflearn.backend.gitExchange.models.Cluster;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.utils.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Value("${selflearn.git.baseDir}")
    private String baseDir;

    private final RestClient restClient;

    @Override
    public RepoTreesDto getRepoTrees() {
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
                .toEntity(RepoTreesDto.class).getBody();
    }

    @Override
    public ContentDto getContent(String path) {
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
                .toEntity(ContentDto.class).getBody();
    }

    @Override
    public List<Cluster> getAllCluster() {
        // Find all json file
        RepoTreesDto repoTreesDto = this.getRepoTrees();
        List<TreeNodeDto> nodes = repoTreesDto
                .getTree()
                .stream()
                .filter(item -> item.getPath().matches(".*\\.json$"))
                .toList();

        List<Cluster> clusters = new ArrayList<>();
        for (TreeNodeDto node : nodes) {
            ContentDto contentDto = this.getContent(baseDir + "/" + node.getPath());
            String base64Content = contentDto.getContent();
            String content = new String(Base64.decodeBase64(base64Content))
                    .replaceAll("\n", "")
                    .replaceAll(" ", "");
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                clusters.addAll(Arrays.asList(objectMapper.readValue(content, Cluster[].class)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return clusters;
    }
}
