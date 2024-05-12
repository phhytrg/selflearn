package com.selflearn.backend.nodePool.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.selflearn.backend.clusters.services.ClusterService;
import com.selflearn.backend.nodePool.NodePool;
import com.selflearn.backend.nodePool.NodePoolRepository;
import com.selflearn.backend.nodePool.dtos.DeleteResourcesResponse;
import com.selflearn.backend.resourceGroups.services.ResourceGroupService;
import com.selflearn.backend.subscriptions.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NodePoolServiceImpl implements NodePoolService {
    private final NodePoolRepository nodePoolRepository;
    private final SubscriptionService subscriptionService;
    private final ResourceGroupService resourceGroupService;
    private final ClusterService clusterService;
    private final ObjectMapper objectMapper;

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
    public List<NodePool> uploadNodePools(String subscriptionName, String resourceGroupName, String clusterName, MultipartFile file) {
        clusterName = clusterName.split("\\.")[clusterName.length() - 1].equals("json")
                ? clusterName
                : clusterName + ".json";
        final String content;
        try {
            content = new String(file.getBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            List<NodePool> nodePools = Arrays.asList(objectMapper.readValue(content, NodePool[].class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

//        subscriptionService.add();
        return List.of();
    }
}
