package com.selflearn.backend.clusters;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClusterRepository extends JpaRepository<Cluster, UUID> {
}
