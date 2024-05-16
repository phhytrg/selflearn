package com.selflearn.backend.nodePool.services;

import com.selflearn.backend.nodePool.NodePool;
import com.selflearn.backend.nodePool.dtos.CreateNodePoolsRequest;
import com.selflearn.backend.nodePool.dtos.DeleteResourcesResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NodePoolService {
    List<NodePool> getNodePools(String subscriptionName, String resourceGroupName, String clusterName);
    DeleteResourcesResponse delete(String subscriptionName, String resourceGroupName, String clusterName);
    public List<NodePool> uploadNodePools(CreateNodePoolsRequest request, MultipartFile file);
}
