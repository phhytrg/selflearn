package com.selflearn.backend.nodePool;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NodePoolRepository extends JpaRepository<NodePool, UUID> {
    List<NodePool> findNodePoolByClusterName(String clusterName);

    @Query(value = "select n.id, n.provisioning_state, n.power_state, " +
            "n.node_size, n.node_image_version, n.node_count, n.k8s_version, n.mode, n.name, n.os, n.cluster_id  " +
            "from node_pools n " +
            "join clusters c on c.id = n.cluster_id " +
            "join resource_groups rg on rg.id = c.resource_group_id " +
            "where rg.name = :resourceGroupName", nativeQuery = true
    )
    List<NodePool> findNodePoolByResourceGroupName(String resourceGroupName);

    @Query(value = "select n.id, n.provisioning_state, n.power_state, " +
            "n.node_size, n.node_image_version, n.node_count, n.k8s_version, n.mode, n.name, n.os, n.cluster_id " +
            "from node_pools n " +
            "join clusters c on c.id = n.cluster_id " +
            "join resource_groups rg on rg.id = c.resource_group_id " +
            "join subscriptions s on s.id = rg.subscription_id " +
            "where s.name = :subscriptionName", nativeQuery = true
    )
    List<NodePool> findNodePoolBySubscriptionName(String subscriptionName);
}
