package com.selflearn.backend.gitExchange.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.selflearn.backend.clusters.Cluster;
import com.selflearn.backend.gitExchange.models.GitCluster;
import com.selflearn.backend.gitExchange.models.GitContent;
import com.selflearn.backend.gitExchange.models.GitRepoTrees;
import com.selflearn.backend.gitExchange.models.GitTreeNode;
import com.selflearn.backend.resourceGroups.ResourceGroup;
import com.selflearn.backend.subscriptions.Subscription;
import com.selflearn.backend.subscriptions.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.utils.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GitExchangeServiceImpl implements GitExchangeService {

    private final SubscriptionService subscriptionService;
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
    public GitRepoTrees getRepoTrees() {
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
                .toEntity(GitRepoTrees.class).getBody();
    }

    @Override
    public GitContent getContent(String path) {
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
                .toEntity(GitContent.class).getBody();
    }

    @Override
    public List<GitCluster> getAllCluster() {
        // Find all json file
        GitRepoTrees repoTreesDto = this.getRepoTrees();
        List<GitTreeNode> nodes = repoTreesDto
                .getTree()
                .stream()
                .filter(item -> item.getPath().matches(".*\\.json$"))
                .toList();

        List<GitCluster> clusters = new ArrayList<>();
        for (GitTreeNode node : nodes) {
            GitContent contentDto = this.getContent(baseDir + "/" + node.getPath());
            String base64Content = contentDto.getContent();
            String content = new String(Base64.decodeBase64(base64Content))
                    .replaceAll("\n", "")
                    .replaceAll(" ", "");
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                clusters.addAll(Arrays.asList(objectMapper.readValue(content, GitCluster[].class)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return clusters;
    }

    @Override
    public List<Subscription> syncWithDatabase() {
        Map<String, Subscription> subscriptions = new HashMap<>();
        Map<String, ResourceGroup> resourceGroups = new HashMap<>();
        GitRepoTrees repoTreesDto = this.getRepoTrees();
        List<GitTreeNode> nodes = repoTreesDto
                .getTree();
        for (GitTreeNode node : nodes) {
            String[] parts = node.getPath().split("/");
            if (parts.length == 1) {
                String subscriptionName = parts[0];
                Subscription subscription = Subscription.builder()
                        .id(UUID.randomUUID())
                        .name(subscriptionName)
                        .resourceGroups(new ArrayList<>())
                        .build();
                subscriptions.put(subscriptionName, subscription);
            } else if (parts.length == 2) {
                String subscriptionName = parts[0];
                String resourceGroupName = parts[1];
                Subscription subscription = subscriptions.computeIfAbsent(subscriptionName, s -> Subscription.builder().name(s).resourceGroups(new ArrayList<>()).build());
                List<ResourceGroup> resourceGroupsList = subscription.getResourceGroups();
                ResourceGroup resourceGroup = ResourceGroup.builder()
                        .id(UUID.randomUUID())
                        .subscription(subscription)
                        .name(resourceGroupName)
                        .clusters(new ArrayList<>())
                        .build();
                resourceGroupsList.add(resourceGroup);
                resourceGroups.put(resourceGroupName, resourceGroup);
            } else {
                String resourceGroupName = parts[1];
                ResourceGroup resourceGroup = resourceGroups.get(resourceGroupName);
                if (resourceGroup == null) {
                    throw new IllegalStateException("Resource group not found: " + resourceGroupName);
                }
                // Fetch cluster content
                GitContent gitContent = this.getContentFromUrl(node.getUrl());
                String base64Content = gitContent.getContent();
                String content = new String(Base64.decodeBase64(base64Content))
                        .replaceAll("\n", "")
                        .replaceAll(" ", "");
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    List<Cluster> clusters = resourceGroup.getClusters();
                    List<Cluster> parsedClusters = Arrays.asList(objectMapper.readValue(content, Cluster[].class));
                    parsedClusters.forEach(cluster -> {
                        cluster.setResourceGroup(resourceGroup);
                        cluster.setId(UUID.randomUUID());
                    });
                    clusters.addAll(parsedClusters);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Error processing JSON", e);
                }
            }
        }

        return subscriptionService.saveAll(subscriptions.values().stream().toList());

    }

    private GitContent getContentFromUrl(String url) {
        return restClient.get().uri(url).header("Authorization", "Bearer " + gitToken).retrieve().toEntity(GitContent.class).getBody();
    }
}
