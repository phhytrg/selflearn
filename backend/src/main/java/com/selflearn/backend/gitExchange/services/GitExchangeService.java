package com.selflearn.backend.gitExchange.services;

import com.selflearn.backend.gitExchange.dtos.CreateCommitResponse;
import com.selflearn.backend.gitExchange.dtos.ContentResponse;
import com.selflearn.backend.gitExchange.dtos.GitRepoTrees;
import com.selflearn.backend.gitExchange.dtos.GitNodePool;
import com.selflearn.backend.subscriptions.Subscription;

import java.util.List;

public interface GitExchangeService {
    GitRepoTrees getRepoTrees();
    ContentResponse getContent(String path);
    List<String> getClusters(String subscriptionName, String resourceGroupName);
    List<Subscription> syncDatabaseWithGithub();
    List<String> getSubscriptions();
    List<String> getResourceGroups(String subscription);
    List<GitNodePool> getNodePools(String subscriptionName, String resourceGroupName, String clusterName);
    CreateCommitResponse syncGithubWithDatabase();
    void syncDatabaseViaWebhook(String request);
}
