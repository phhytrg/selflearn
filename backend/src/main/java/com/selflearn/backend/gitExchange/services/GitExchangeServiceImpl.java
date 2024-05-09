package com.selflearn.backend.gitExchange.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.selflearn.backend.clusters.Cluster;
import com.selflearn.backend.gitExchange.models.GitNodePool;
import com.selflearn.backend.gitExchange.models.GitContent;
import com.selflearn.backend.gitExchange.models.GitRepoTrees;
import com.selflearn.backend.gitExchange.models.GitTreeNode;
import com.selflearn.backend.nodePool.NodePool;
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

    @Value("${selflearn.git.observeBranch}")
    private String observeBranch;

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
                                .queryParam("ref", observeBranch)
                                .build(gitOwner, gitRepo, path))
                .header("Authorization", "Bearer " + gitToken)
                .retrieve()
                .toEntity(GitContent.class).getBody();
    }

    @Override
    public List<String> getClusters(String subscriptionName, String resourceGroupName) {
        if (resourceGroupName != null) {
            return this.getRepoTrees().getTree().stream()
                    .filter(gitTreeNode -> {
                        String[] parts = gitTreeNode.getPath().split("/");
                        return parts.length == 3 && parts[1].equals(resourceGroupName);
                    }).map(gitContent -> {
                        String[] parts = gitContent.getPath().split("/");
                        return parts[parts.length - 1];
                    }).toList();
        }
        if (subscriptionName != null) {
            return this.getRepoTrees().getTree().stream()
                    .filter(gitTreeNode -> {
                        String[] parts = gitTreeNode.getPath().split("/");
                        return parts.length == 3 && parts[0].equals(subscriptionName);
                    }).map(gitContent -> {
                        String[] parts = gitContent.getPath().split("/");
                        return parts[parts.length - 1];
                    }).toList();
        }
        return this.getRepoTrees().getTree().stream()
                .filter(gitTreeNode -> gitTreeNode.getPath().split("/").length == 3)
                .map(gitTreeNode -> gitTreeNode.getPath().split("/")[2]).toList();
    }


    @Override
    public List<GitNodePool> getNodePools(String subscriptionName, String resourceGroupName, String clusterName) {
        if (clusterName != null) {
            List<String> urls = this.getRepoTrees().getTree().stream()
                    .filter(gitTreeNode -> {
                        String[] parts = gitTreeNode.getPath().split("/");
                        return parts.length == 3 && parts[2].equals(clusterName);
                    })
                    .map(GitTreeNode::getUrl).toList();
            return this.getNodePoolsFromUrl(urls);
        }
        if (resourceGroupName != null) {
            List<String> urls = this.getRepoTrees().getTree().stream()
                    .filter(gitTreeNode -> {
                        String[] parts = gitTreeNode.getPath().split("/");
                        return parts.length == 3 && parts[1].equals(resourceGroupName);
                    })
                    .map(GitTreeNode::getUrl).toList();
            return this.getNodePoolsFromUrl(urls);
        }
        if (subscriptionName != null) {
            List<String> urls = this.getRepoTrees().getTree().stream()
                    .filter(gitTreeNode -> {
                        String[] parts = gitTreeNode.getPath().split("/");
                        return parts.length == 3 && parts[0].equals(subscriptionName);
                    })
                    .map(GitTreeNode::getUrl).toList();
            return this.getNodePoolsFromUrl(urls);
        }
        return List.of();
    }

    @Override
    public List<Subscription> syncWithDatabase() {
        this.subscriptionService.deleteAll();

        Map<String, Subscription> subscriptions = new HashMap<>();
        Map<String, ResourceGroup> resourceGroups = new HashMap<>();
        GitRepoTrees repoTreesDto = this.getRepoTrees();
        List<GitTreeNode> nodes = repoTreesDto
                .getTree();
        for (GitTreeNode node : nodes) {
            String[] parts = node.getPath().split("/");
            String sha = node.getSha();
            if (parts.length == 1) {
                String subscriptionName = parts[0];
                Subscription subscription = Subscription.builder()
                        .id(UUID.randomUUID())
                        .name(subscriptionName)
                        .resourceGroups(new ArrayList<>())
                        .sha(sha)
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
                        .sha(sha)
                        .build();
                resourceGroupsList.add(resourceGroup);
                resourceGroups.put(resourceGroupName, resourceGroup);
            } else {
                String resourceGroupName = parts[1];
                ResourceGroup resourceGroup = resourceGroups.get(resourceGroupName);
                if (resourceGroup == null) {
                    throw new IllegalStateException("Resource group not found: " + resourceGroupName);
                }

                GitContent gitContent = this.getContentFromUrl(node.getUrl());
                String base64Content = gitContent.getContent();
                String content = new String(Base64.decodeBase64(base64Content))
                        .replaceAll("\n", "")
                        .replaceAll(" ", "");
                // Fetch cluster content
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    List<Cluster> clusters = resourceGroup.getClusters();
                    List<NodePool> nodePools = Arrays.asList(objectMapper.readValue(content, NodePool[].class));
                    nodePools.forEach(nodePool -> nodePool.setId(UUID.randomUUID()));
                    Cluster cluster = Cluster.builder()
                            .id(UUID.randomUUID())
                            .name(parts[2])
                            .resourceGroup(resourceGroup)
                            .nodePools(nodePools)
                            .sha(sha)
                            .build();
                    nodePools.forEach(nodePool -> nodePool.setCluster(cluster));
                    clusters.add(cluster);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Error processing JSON", e);
                }
            }
        }

        return subscriptionService.saveAll(subscriptions.values().stream().toList());

    }

    @Override
    public List<String> getSubscriptions() {
        GitRepoTrees repoTrees = restClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .host(gitApiHost)
                                .scheme("https")
                                .path("/repos/{owner}/{repo}/git/trees/{sha}")
                                .build(gitOwner, gitRepo, gitSampleDirSha))
                .header("Authorization", "Bearer " + gitToken)
                .retrieve()
                .toEntity(GitRepoTrees.class).getBody();
        if (repoTrees == null) {
            return List.of();
        }
        return repoTrees.getTree().stream().map(GitTreeNode::getPath).toList();
    }

    @Override
    public List<String> getResourceGroups(String subscription) {

        if (subscription == null) {
            return this.getRepoTrees().getTree().stream()
                    .filter(gitTreeNode -> gitTreeNode.getPath().split("/").length == 2)
                    .map(gitTreeNode -> gitTreeNode.getPath().split("/")[1]).toList();
        }
        // Not duplicate code, just optimize performance
        GitContent[] gitContents = restClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .host(gitApiHost)
                                .scheme("https")
                                .path("/repos/{owner}/{repo}/contents/" +
                                        baseDir + "/" + subscription)
                                .queryParam("ref", observeBranch)
                                .build(gitOwner, gitRepo))
                .header("Authorization", "Bearer " + gitToken)
                .retrieve()
                .toEntity(GitContent[].class).getBody();
        if (gitContents == null) {
            return List.of();
        }
        return Arrays.stream(gitContents).map(gitContent -> {
            String[] parts = gitContent.getPath().split("/");
            return parts[parts.length - 1];
        }).toList();
    }

    private GitContent getContentFromUrl(String url) {
        return restClient.get().uri(url).header("Authorization", "Bearer " + gitToken).retrieve().toEntity(GitContent.class).getBody();
    }


    private List<GitNodePool> getNodePoolsFromUrl(List<String> urls) {
        // Find all json file
        List<GitNodePool> clusters = new ArrayList<>();
        for (String url : urls) {
            GitContent contentDto = this.getContentFromUrl(url);
            String base64Content = contentDto.getContent();
            String content = new String(Base64.decodeBase64(base64Content))
                    .replaceAll("\n", "")
                    .replaceAll(" ", "");
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                clusters.addAll(Arrays.asList(objectMapper.readValue(content, GitNodePool[].class)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return clusters;
    }
}
