package com.selflearn.backend.clusters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ClusterRepository extends JpaRepository<Cluster, UUID> {
    List<Cluster> findClusterByResourceGroupName(String resourceName);

    @Query(value = "select c.id, c.k8s_version, c.mode , c.name ,c.node_count , c.node_image_version , c.node_size, " +
            "c.os ,c.power_state , c.provisioning_state, c.resource_group_id  " +
            "from clusters c " +
            "join resource_groups rg on c.resource_group_id = rg.id " +
            "join subscriptions s on s.id = rg.subscription_id " +
            "where s.name = :subscriptionName", nativeQuery = true)
    List<Cluster> findClusterBySubscriptionName(String subscriptionName);

    List<Cluster> findClusterByName(String clusterName);
}
