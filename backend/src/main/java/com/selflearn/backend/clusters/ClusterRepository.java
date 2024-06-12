package com.selflearn.backend.clusters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ClusterRepository extends JpaRepository<Cluster, UUID> {
    List<Cluster> findClusterByResourceGroupName(String resourceName);

    @Query(value = "select c.id, c.name, c.resource_group_id, c.sha " +
            "from clusters c " +
            "join resource_groups rg on c.resource_group_id = rg.id " +
            "join subscriptions s on s.id = rg.subscription_id " +
            "where s.name = :subscriptionName", nativeQuery = true)
    List<Cluster> findClusterBySubscriptionName(String subscriptionName);


    @Query(value = "select c.id, c.name, c.resource_group_id, c.sha " +
            "from clusters c " +
            "join resource_groups rg on c.resource_group_id = rg.id " +
            "join subscriptions s on s.id = rg.subscription_id " +
            "where s.name = :subscriptionName and rg.name = :resourceGroupName", nativeQuery = true)
    List<Cluster> findClusterBySubscriptionNameAndResourceGroupName(String subscriptionName, String resourceGroupName);

    Cluster findByName(String clusterName);
}
