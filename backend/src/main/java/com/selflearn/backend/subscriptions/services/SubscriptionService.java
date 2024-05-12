package com.selflearn.backend.subscriptions.services;

import com.selflearn.backend.nodePool.NodePool;
import com.selflearn.backend.nodePool.dtos.DeleteResourcesResponse;
import com.selflearn.backend.subscriptions.Subscription;

import java.util.List;

public interface SubscriptionService {
    List<Subscription> saveAll(List<Subscription> subscriptions);
    List<Subscription> fetchAll();
    void deleteAll();
    DeleteResourcesResponse deleteSubscription(String subscriptionName);
    Subscription saveSubscription(Subscription subscription);
}
