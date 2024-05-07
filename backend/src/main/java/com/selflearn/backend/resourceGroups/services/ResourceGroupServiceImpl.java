package com.selflearn.backend.resourceGroups.services;

import com.selflearn.backend.resourceGroups.ResourceGroup;
import com.selflearn.backend.resourceGroups.ResourceGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceGroupServiceImpl implements ResourceGroupService{
    private final ResourceGroupRepository resourceGroupRepository;


    @Override
    public List<ResourceGroup> fetchAll() {
        return resourceGroupRepository.findAll();
    }
}
