package com.selflearn.backend.clusters.services;

import com.selflearn.backend.clusters.Cluster;

import java.util.List;

public interface ClusterService {
    List<Cluster> fetchAll();
}
