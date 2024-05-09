package com.selflearn.backend.gitExchange.daos;

import com.selflearn.backend.gitExchange.models.GitRepoTrees;

public interface GitExchangeDao {
    GitRepoTrees getRepoTrees(String sha);
    String getLatestSampleDirSha();
}
