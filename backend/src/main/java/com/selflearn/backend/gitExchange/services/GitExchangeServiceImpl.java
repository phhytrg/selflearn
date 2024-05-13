package com.selflearn.backend.gitExchange.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.selflearn.backend.clusters.Cluster;
import com.selflearn.backend.gitExchange.daos.GitExchangeDao;
import com.selflearn.backend.gitExchange.dtos.CreateBlobResponse;
import com.selflearn.backend.gitExchange.dtos.CreateCommitResponse;
import com.selflearn.backend.gitExchange.dtos.CreateTreeRequest;
import com.selflearn.backend.gitExchange.dtos.CreateTreeResponse;
import com.selflearn.backend.gitExchange.dtos.GitNodePool;
import com.selflearn.backend.gitExchange.dtos.ContentResponse;
import com.selflearn.backend.gitExchange.dtos.GitRepoTrees;
import com.selflearn.backend.gitExchange.dtos.GitTreeNode;
import com.selflearn.backend.nodePool.NodePool;
import com.selflearn.backend.resourceGroups.ResourceGroup;
import com.selflearn.backend.subscriptions.Subscription;
import com.selflearn.backend.subscriptions.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.utils.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    @Value("${selflearn.git.api.host}")
    private String gitApiHost;

    @Value("${selflearn.git.token}")
    private String gitToken;

    @Value("${selflearn.git.baseDir}")
    private String baseDir;

    @Value("${selflearn.git.observeBranch}")
    private String observeBranch;

    private final ObjectMapper objectMapper;

    private final GitExchangeDao gitExchangeDao;

    @Override
    public GitRepoTrees getRepoTrees() {
        return gitExchangeDao.getRepoTrees(this.gitExchangeDao.getLatestSampleDirSha(), true);
    }

    @Override
    public ContentResponse getContent(String path) {
        return this.gitExchangeDao.getContentFromPath(path);
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
    public CreateCommitResponse syncGithubWithDatabase() {
        List<Subscription> subscriptions = subscriptionService.fetchAll();
        List<CreateTreeRequest.TreeNodeRequest> subscriptionNodes = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            List<CreateTreeRequest.TreeNodeRequest> resourceNodes = new ArrayList<>();
            for (ResourceGroup resourceGroup : subscription.getResourceGroups()) {
                List<CreateTreeRequest.TreeNodeRequest> clusterNodes = new ArrayList<>();
                for (Cluster cluster : resourceGroup.getClusters()) {
                    CreateBlobResponse createCluster;
                    try {
                        createCluster = this.gitExchangeDao.createBlob(objectMapper.writeValueAsString(cluster.getNodePools()));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    clusterNodes.add(CreateTreeRequest.TreeNodeRequest.builder()
                            .mode("100644")
                            .path(cluster.getName())
                            .type("blob")
                            .sha(createCluster.getSha())
                            .build());
                }
                CreateTreeResponse resourceGroupTree = this.gitExchangeDao.createTree(
                        resourceGroup.getSha(),
                        clusterNodes
                );

                resourceNodes.add(CreateTreeRequest.TreeNodeRequest.builder()
                        .mode("040000")
                        .path(resourceGroup.getName())
                        .type("tree")
                        .sha(resourceGroupTree.getSha())
                        .build());
            }
            CreateTreeResponse subscriptionTree = this.gitExchangeDao.createTree(
                    subscription.getSha(),
                    resourceNodes
            );
            subscriptionNodes.add(CreateTreeRequest.TreeNodeRequest.builder()
                    .mode("040000")
                    .path(subscription.getName())
                    .type("tree")
                    .sha(subscriptionTree.getSha())
                    .build());
        }

        CreateTreeResponse sampleTree = this.gitExchangeDao.createTree(
                gitExchangeDao.getLatestSampleDirSha(),
                subscriptionNodes
        );

        String latestSha = this.gitExchangeDao.getLatestSha();
        CreateTreeResponse createSampleDir = this.gitExchangeDao.createTree(
                latestSha,
                List.of(CreateTreeRequest.TreeNodeRequest.builder()
                        .mode("040000")
                        .path(baseDir)
                        .type("tree")
                        .sha(sampleTree.getSha())
                        .build())
        );

        CreateCommitResponse response = this.gitExchangeDao.createCommit(
                createSampleDir.getSha(),
                latestSha,
                "Sync data from database");

        return this.gitExchangeDao.updateReference(response);
    }

    @Override
    public List<Subscription> syncDatabaseWithGithub() {
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

                ContentResponse gitContent = this.gitExchangeDao.getContentFromUrl(node.getUrl());
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
        return this.gitExchangeDao.getRepoTrees(this.gitExchangeDao.getLatestSampleDirSha(), false)
                .getTree().stream()
                .map(GitTreeNode::getPath).toList();
    }

    @Override
    public List<String> getResourceGroups(String subscription) {

        if (subscription == null) {
            return this.getRepoTrees().getTree().stream()
                    .filter(gitTreeNode -> gitTreeNode.getPath().split("/").length == 2)
                    .map(gitTreeNode -> gitTreeNode.getPath().split("/")[1]).toList();
        }
        // Not duplicate code, just optimize performance
        ContentResponse[] gitContents = this.gitExchangeDao.getContentsFromPath(baseDir + "/" + subscription);
        if (gitContents == null) {
            return List.of();
        }
        return Arrays.stream(gitContents).map(gitContent -> {
            String[] parts = gitContent.getPath().split("/");
            return parts[parts.length - 1];
        }).toList();
    }

    private List<GitNodePool> getNodePoolsFromUrl(List<String> urls) {
        // Find all json file
        List<GitNodePool> clusters = new ArrayList<>();
        for (String url : urls) {
            ContentResponse contentDto = this.gitExchangeDao.getContentFromUrl(url);
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
