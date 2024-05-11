package com.selflearn.backend.resourceGroups.services;

import com.selflearn.backend.nodePool.dtos.DeleteResourcesResponse;
import com.selflearn.backend.resourceGroups.ResourceGroup;

import java.util.List;

public interface ResourceGroupService {
    List<ResourceGroup> fetchAllBySubscriptionId(String subscriptionName);

    DeleteResourcesResponse deleteResourceGroup(String resourceGroupName);
}
