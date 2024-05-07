package com.selflearn.backend.gitExchange.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.UUID;

@Data
@Entity(name = "clusters")
public class Cluster {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String provisioningState;
    private String powerState;
    private int nodeCount;
    private String mode;
    private String nodeImageVersion;
    private String k8sVersion;
    private String nodeSize;
    private String os;

    @ManyToOne
    private ResourceGroup resourceGroup;
}
