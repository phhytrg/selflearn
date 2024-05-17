package com.selflearn.backend.nodePool.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.selflearn.backend.clusters.Cluster;
import com.selflearn.backend.clusters.services.ClusterService;
import com.selflearn.backend.gitExchange.daos.GitExchangeDao;
import com.selflearn.backend.gitExchange.dtos.CreateBlobResponse;
import com.selflearn.backend.gitExchange.dtos.CreateTreeRequest;
import com.selflearn.backend.gitExchange.dtos.CreateTreeResponse;
import com.selflearn.backend.nodePool.NodePool;
import com.selflearn.backend.nodePool.NodePoolRepository;
import com.selflearn.backend.nodePool.dtos.CreateNodePoolsRequest;
import com.selflearn.backend.nodePool.dtos.DeleteResourcesResponse;
import com.selflearn.backend.resourceGroups.ResourceGroup;
import com.selflearn.backend.resourceGroups.services.ResourceGroupService;
import com.selflearn.backend.subscriptions.Subscription;
import com.selflearn.backend.subscriptions.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NodePoolServiceImpl implements NodePoolService {
    private final NodePoolRepository nodePoolRepository;
    private final SubscriptionService subscriptionService;
    private final ResourceGroupService resourceGroupService;
    private final ClusterService clusterService;
    private final ObjectMapper objectMapper;
    private final GitExchangeDao gitExchangeDao;

    @Override
    public List<NodePool> getNodePools(String subscriptionName, String resourceGroupName, String clusterName) {
        if (clusterName != null) {
            return nodePoolRepository.findNodePoolByClusterName(clusterName);
        }
        if (resourceGroupName != null) {
            return nodePoolRepository.findNodePoolByResourceGroupName(resourceGroupName);
        }
        if (subscriptionName != null) {
            return nodePoolRepository.findNodePoolBySubscriptionName(subscriptionName);
        }
        return new ArrayList<>();
    }

    @Override
    public DeleteResourcesResponse delete(String subscriptionName, String resourceGroupName, String clusterName) {
        if (!clusterName.isEmpty()) {
            return clusterService.deleteCluster(clusterName);
        }
        if (!resourceGroupName.isEmpty()) {
            return resourceGroupService.deleteResourceGroup(resourceGroupName);
        }
        if (!subscriptionName.isEmpty()) {
            return subscriptionService.deleteSubscription(subscriptionName);
        }
        return new DeleteResourcesResponse();
    }

    @Override
    public List<NodePool> uploadNodePools(CreateNodePoolsRequest request, MultipartFile file) {

        final String content;
        final List<NodePool> nodePools;
        try {
            content = new String(file.getBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            nodePools = Arrays.asList(objectMapper.readValue(content, NodePool[].class));
            nodePools.forEach(nodePool -> nodePool.setId(UUID.randomUUID()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        CreateBlobResponse clusterBlob;
        try {
            clusterBlob = this.gitExchangeDao.createBlob(
                    objectMapper.writeValueAsString(nodePools)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Cluster cluster = clusterService.findByName(request.clusterName());
        if (cluster != null) {
            clusterService.deleteCluster(request.clusterName());
        }
        cluster = Cluster.builder()
                .name(request.clusterName())
                .nodePools(nodePools)
                .sha(clusterBlob.getSha())
                .resourceGroup(null)
                .id(UUID.randomUUID())
                .build();
        Cluster finalCluster = cluster;
        nodePools.forEach(nodePool -> nodePool.setCluster(finalCluster));

        // Resource group
        ResourceGroup resourceGroup = resourceGroupService.findByName(request.resourceGroupName());
        if (resourceGroup == null) {
            resourceGroup = ResourceGroup.builder()
                    .name(request.resourceGroupName())
                    .clusters(new ArrayList<>() {{
                        add(finalCluster);
                    }})
                    .build();
        } else {
            resourceGroup.getClusters().add(cluster);
        }
        final CreateTreeResponse resourceGroupTree = this.gitExchangeDao.createTree(
                null,
                resourceGroup.getClusters().stream().map(
                        c -> CreateTreeRequest.TreeNodeRequest.builder()
                                .path(request.clusterName())
                                .sha(c.getSha())
                                .mode("100644")
                                .type("blob")
                                .build()
                ).toList()
        );
        resourceGroup.setSha(resourceGroupTree.getSha());
        cluster.setResourceGroup(resourceGroup);

        //Subscription
        Subscription subscription = subscriptionService.findByName(request.subscriptionName());
        if (subscription == null) {
            ResourceGroup finalResourceGroup = resourceGroup;
            subscription = Subscription.builder()
                    .name(request.subscriptionName())
                    .resourceGroups(new ArrayList<>() {{
                        add(finalResourceGroup);
                    }})
                    .id(UUID.randomUUID())
                    .build();
        } else {
            subscription.getResourceGroups().add(resourceGroup);
        }
        final CreateTreeResponse subscriptionTree = this.gitExchangeDao.createTree(
                null,
                subscription.getResourceGroups().stream().map(
                        rg -> CreateTreeRequest.TreeNodeRequest.builder()
                                .type("tree")
                                .path(rg.getName())
                                .sha(rg.getSha())
                                .mode("040000")
                                .build()
                ).toList()
        );
        subscription.setSha(subscriptionTree.getSha());
        resourceGroup.setSubscription(subscription);

        subscriptionService.saveSubscription(subscription);
        return nodePools;
    }
}
