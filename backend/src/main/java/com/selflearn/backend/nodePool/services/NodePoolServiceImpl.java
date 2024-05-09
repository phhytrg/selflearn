package com.selflearn.backend.nodePool.services;

import com.selflearn.backend.nodePool.NodePool;
import com.selflearn.backend.nodePool.NodePoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NodePoolServiceImpl implements NodePoolService {
    private final NodePoolRepository nodePoolRepository;
    @Override
    public List<NodePool> getNodePools(String subscriptionName, String resourceGroupName, String clusterName) {
        if(clusterName != null){
            return nodePoolRepository.findNodePoolByClusterName(clusterName);
        }
        if(resourceGroupName != null){
            return nodePoolRepository.findNodePoolByResourceGroupName(resourceGroupName);
        }
        if(subscriptionName != null) {
            return nodePoolRepository.findNodePoolBySubscriptionName(subscriptionName);
        }
        return new ArrayList<>();
    }
}
