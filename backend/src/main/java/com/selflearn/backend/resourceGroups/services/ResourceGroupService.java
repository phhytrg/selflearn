package com.selflearn.backend.resourceGroups.services;

import com.selflearn.backend.resourceGroups.ResourceGroup;

import java.util.List;
import java.util.UUID;

public interface ResourceGroupService {
    List<ResourceGroup> fetchAllBySubscriptionId(String subscriptionName);
}
