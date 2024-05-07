package com.selflearn.backend.gitExchange.services;

import com.selflearn.backend.gitExchange.dtos.ContentDto;
import com.selflearn.backend.gitExchange.dtos.RepoTreesDto;
import com.selflearn.backend.gitExchange.models.Cluster;

import java.util.List;

public interface GitExchangeService {
    RepoTreesDto getRepoTrees();
    ContentDto getContent(String path);
    List<Cluster> getAllCluster();
}
