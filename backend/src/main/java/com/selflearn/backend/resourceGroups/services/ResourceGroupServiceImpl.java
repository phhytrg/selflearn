package com.selflearn.backend.resourceGroups.services;

import com.selflearn.backend.nodePool.dtos.DeleteResourcesResponse;
import com.selflearn.backend.resourceGroups.ResourceGroup;
import com.selflearn.backend.resourceGroups.ResourceGroupRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceGroupServiceImpl implements ResourceGroupService {
    private final ResourceGroupRepository resourceGroupRepository;

    @Override
    public List<ResourceGroup> fetchAllBySubscriptionId(@Nullable String subscriptionName) {
        if (subscriptionName == null) {
            return resourceGroupRepository.findAll();
        }
        return resourceGroupRepository.findResourceGroupBySubscriptionName(subscriptionName);
    }

    @Override
    public DeleteResourcesResponse deleteResourceGroup(String resourceGroupName) {
        ResourceGroup resourceGroup = resourceGroupRepository.findByName(resourceGroupName);
        resourceGroupRepository.delete(resourceGroup);
        return DeleteResourcesResponse.builder()
                .noResourceGroupsDeleted(1)
                .noClustersDeleted(resourceGroup.getClusters().size())
                .noNodePoolsDeleted(resourceGroup.getClusters().stream()
                        .mapToLong(cluster -> cluster.getNodePools().size())
                        .sum())
                .build();
    }
}
