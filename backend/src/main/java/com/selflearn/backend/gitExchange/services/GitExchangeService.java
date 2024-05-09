package com.selflearn.backend.gitExchange.services;

import com.selflearn.backend.gitExchange.models.GitContent;
import com.selflearn.backend.gitExchange.models.GitRepoTrees;
import com.selflearn.backend.gitExchange.models.GitNodePool;
import com.selflearn.backend.subscriptions.Subscription;

import java.util.List;

public interface GitExchangeService {
    GitRepoTrees getRepoTrees();
    GitContent getContent(String path);
    List<String> getClusters(String subscriptionName, String resourceGroupName);
    List<Subscription> syncWithDatabase();
    List<String> getSubscriptions();
    List<String> getResourceGroups(String subscription);
    List<GitNodePool> getNodePools(String subscriptionName, String resourceGroupName, String clusterName);
}
