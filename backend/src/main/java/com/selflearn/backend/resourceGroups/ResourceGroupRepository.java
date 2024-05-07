package com.selflearn.backend.resourceGroups;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResourceGroupRepository extends JpaRepository<ResourceGroup, UUID> {

}
