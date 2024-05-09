package com.selflearn.backend.nodePool.services;

import com.selflearn.backend.nodePool.NodePool;

import java.util.List;

public interface NodePoolService {
    List<NodePool> getNodePools(String subscriptionName, String resourceGroupName, String clusterName);
}
