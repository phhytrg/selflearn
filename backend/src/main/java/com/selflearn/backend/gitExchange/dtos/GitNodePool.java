package com.selflearn.backend.gitExchange.dtos;

import lombok.Data;

@Data
public class GitNodePool {
    private String name;
    private String provisioningState;
    private String powerState;
    private int nodeCount;
    private String mode;
    private String nodeImageVersion;
    private String k8sVersion;
    private String nodeSize;
    private String os;
}
