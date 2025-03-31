package com.selflearn.backend.nodePool.dtos;

public record CreateNodePoolsRequest (
        String subscriptionName,
        String resourceGroupName,
        String clusterName){
    public String clusterName(){
        String[] parts = clusterName.split("\\.");
        return parts[parts.length - 1].equals("json")
                ? clusterName
                : clusterName + ".json";
    }
}
