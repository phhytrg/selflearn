package com.selflearn.backend.clusters.services;

import com.selflearn.backend.clusters.Cluster;
import com.selflearn.backend.clusters.ClusterRepository;
import com.selflearn.backend.nodePool.dtos.DeleteResourcesResponse;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClusterServiceImpl implements ClusterService {

    private final ClusterRepository clusterRepository;

    @Override
    public List<Cluster> getClusters(@Nullable String subscriptionName, @Nullable String resourceName) {
//        if (resourceName != null) {
//            return clusterRepository.findClusterByResourceGroupName(resourceName);
//        }
//        if (subscriptionName != null) {
//            return clusterRepository.findClusterBySubscriptionName(subscriptionName);
//        }
//
//        return clusterRepository.findAll();
        if (subscriptionName != null && resourceName != null) {
            return clusterRepository.findClusterBySubscriptionNameAndResourceGroupName(subscriptionName, resourceName);
        }
        return List.of();
    }

    @Transactional
    @Override
    public DeleteResourcesResponse deleteCluster(String clusterName) {
        Cluster cluster = clusterRepository.findByName(clusterName);
        clusterRepository.delete(cluster);
        return DeleteResourcesResponse.builder()
                .noClustersDeleted(1)
                .noNodePoolsDeleted(cluster.getNodePools().size())
                .build();
    }

    @Override
    public Cluster findByName(String clusterName) {
        return clusterRepository.findByName(clusterName);
    }
}
