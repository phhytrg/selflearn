package com.selflearn.backend.clusters.services;

import com.selflearn.backend.clusters.Cluster;
import com.selflearn.backend.clusters.ClusterRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClusterServiceImpl implements ClusterService {

    private final ClusterRepository clusterRepository;

    @Override
    public List<Cluster> filter(@Nullable String subscriptionName, @Nullable String resourceName, @Nullable String clusterName) {
        if(clusterName != null){
            return clusterRepository.findClusterByName(clusterName);
        }
        if(resourceName != null){
            return clusterRepository.findClusterByResourceGroupName(resourceName);
        }
        if(subscriptionName != null) {
            return clusterRepository.findClusterBySubscriptionName(subscriptionName);
        }
        return new ArrayList<>();
    }
}
