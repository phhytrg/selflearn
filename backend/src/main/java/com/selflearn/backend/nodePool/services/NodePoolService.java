package com.selflearn.backend.nodePool.services;

import com.selflearn.backend.nodePool.NodePool;
import com.selflearn.backend.nodePool.dtos.DeleteResourcesResponse;

import java.util.List;

public interface NodePoolService {
    List<NodePool> getNodePools(String subscriptionName, String resourceGroupName, String clusterName);
    DeleteResourcesResponse delete(String subscriptionName, String resourceGroupName, String clusterName);
}
