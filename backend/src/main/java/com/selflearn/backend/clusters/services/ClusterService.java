package com.selflearn.backend.clusters.services;

import com.selflearn.backend.clusters.Cluster;
import com.selflearn.backend.nodePool.dtos.DeleteResourcesResponse;
import jakarta.annotation.Nullable;

import java.util.List;

public interface ClusterService {
    List<Cluster> getClusters(@Nullable String subscriptionName, @Nullable String resourceName);

    DeleteResourcesResponse deleteCluster(String clusterName);

    Cluster findByName(String clusterName);
}
