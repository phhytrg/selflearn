package com.selflearn.backend.gitExchange.services;

import com.selflearn.backend.gitExchange.models.GitContent;
import com.selflearn.backend.gitExchange.models.GitRepoTrees;
import com.selflearn.backend.gitExchange.models.GitCluster;
import com.selflearn.backend.subscriptions.Subscription;

import java.util.List;

public interface GitExchangeService {
    GitRepoTrees getRepoTrees();
    GitContent getContent(String path);
    List<GitCluster> getAllCluster();
    List<Subscription> syncWithDatabase();
}
