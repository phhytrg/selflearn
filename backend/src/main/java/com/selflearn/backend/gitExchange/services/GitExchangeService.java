package com.selflearn.backend.gitExchange.services;

import com.selflearn.backend.gitExchange.models.RepoTrees;

public interface GitExchangeService {
    RepoTrees getRepoTrees(String owner, String repo);
}
