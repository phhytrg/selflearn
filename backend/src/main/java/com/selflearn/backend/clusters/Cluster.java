package com.selflearn.backend.clusters;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.selflearn.backend.resourceGroups.ResourceGroup;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.UUID;


@Data
@Entity(name = "clusters")
public class Cluster {
    @Id
    private UUID id;
    @Column(unique = true)
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
    @JsonBackReference
    private ResourceGroup resourceGroup;
}
