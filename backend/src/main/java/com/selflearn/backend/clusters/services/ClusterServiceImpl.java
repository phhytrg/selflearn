package com.selflearn.backend.clusters.services;

import com.selflearn.backend.clusters.Cluster;
import com.selflearn.backend.clusters.ClusterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClusterServiceImpl implements ClusterService {
    private final ClusterRepository clusterRepository;
    @Override
    public List<Cluster> fetchAll() {
        return clusterRepository.findAll();
    }
}
