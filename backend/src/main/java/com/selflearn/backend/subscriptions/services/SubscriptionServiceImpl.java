package com.selflearn.backend.subscriptions.services;

import com.selflearn.backend.clusters.Cluster;
import com.selflearn.backend.nodePool.NodePool;
import com.selflearn.backend.nodePool.dtos.DeleteResourcesResponse;
import com.selflearn.backend.resourceGroups.ResourceGroup;
import com.selflearn.backend.subscriptions.Subscription;
import com.selflearn.backend.subscriptions.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public List<Subscription> saveAll(List<Subscription> subscriptions) {
        return subscriptionRepository.saveAll(subscriptions);
    }

    @Override
    public List<Subscription> fetchAll() {
        return subscriptionRepository.findAll();
    }

    @Override
    public void deleteAll() {
        subscriptionRepository.deleteAll();
    }

    @Override
    public DeleteResourcesResponse deleteSubscription(String subscriptionName) {
        Subscription subscription = subscriptionRepository.findByName(subscriptionName);
        subscriptionRepository.delete(subscription);
        return DeleteResourcesResponse.builder()
                .noSubscriptionsDeleted(1)
                .noResourceGroupsDeleted(subscription.getResourceGroups().size())
                .noClustersDeleted(subscription.getResourceGroups().stream()
                        .mapToLong(resourceGroup -> resourceGroup.getClusters().size())
                        .sum())
                .noNodePoolsDeleted(subscription.getResourceGroups().stream()
                        .mapToLong(resourceGroup -> resourceGroup.getClusters().stream()
                                .mapToLong(cluster -> cluster.getNodePools().size())
                                .sum())
                        .sum())
                .build();
    }

    @Override
    public Subscription saveSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }
}
