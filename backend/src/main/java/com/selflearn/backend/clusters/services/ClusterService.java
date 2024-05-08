package com.selflearn.backend.clusters.services;

import com.selflearn.backend.clusters.Cluster;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.UUID;

public interface ClusterService {
    List<Cluster> filter(@Nullable String subscriptionName, @Nullable String resourceName, @Nullable String clusterName);
}
