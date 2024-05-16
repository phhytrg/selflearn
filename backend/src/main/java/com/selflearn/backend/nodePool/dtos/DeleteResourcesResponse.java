package com.selflearn.backend.nodePool.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeleteResourcesResponse {
    private long noNodePoolsDeleted = 0;
    private long noClustersDeleted = 0;
    private long noResourceGroupsDeleted = 0;
    private long noSubscriptionsDeleted = 0;
}
