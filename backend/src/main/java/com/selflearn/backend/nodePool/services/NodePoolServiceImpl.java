package com.selflearn.backend.nodePool.services;

import com.selflearn.backend.clusters.services.ClusterService;
import com.selflearn.backend.nodePool.NodePool;
import com.selflearn.backend.nodePool.NodePoolRepository;
import com.selflearn.backend.nodePool.dtos.DeleteResourcesResponse;
import com.selflearn.backend.resourceGroups.services.ResourceGroupService;
import com.selflearn.backend.subscriptions.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NodePoolServiceImpl implements NodePoolService {
    private final NodePoolRepository nodePoolRepository;
    private final SubscriptionService subscriptionService;
    private final ResourceGroupService resourceGroupService;
    private final ClusterService clusterService;

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
}
