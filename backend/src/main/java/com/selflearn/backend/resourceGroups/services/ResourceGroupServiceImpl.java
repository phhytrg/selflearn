package com.selflearn.backend.resourceGroups.services;

import com.selflearn.backend.resourceGroups.ResourceGroup;
import com.selflearn.backend.resourceGroups.ResourceGroupRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResourceGroupServiceImpl implements ResourceGroupService{
    private final ResourceGroupRepository resourceGroupRepository;

    @Override
    public List<ResourceGroup> fetchAllBySubscriptionId(@Nullable String subscriptionName) {
        if(subscriptionName == null){
            return resourceGroupRepository.findAll();
        }
        return resourceGroupRepository.findResourceGroupBySubscriptionName(subscriptionName);
    }
}
