package com.selflearn.backend.gitExchange.services;

import com.selflearn.backend.gitExchange.models.GitContent;
import com.selflearn.backend.gitExchange.models.GitRepoTrees;
import com.selflearn.backend.gitExchange.models.GitCluster;
import com.selflearn.backend.subscriptions.Subscription;

import java.util.List;

public interface GitExchangeService {
    GitRepoTrees getRepoTrees();
    GitContent getContent(String path);
    List<GitCluster> getClusters(String subscriptionName, String resourceGroupName, String clusterName);
    List<Subscription> syncWithDatabase();
    List<String> getSubscriptions();
    List<String> getResourceGroups(String subscription);
}
