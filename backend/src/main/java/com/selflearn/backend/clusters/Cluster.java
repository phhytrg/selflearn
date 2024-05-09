package com.selflearn.backend.clusters;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.selflearn.backend.nodePool.NodePool;
import com.selflearn.backend.resourceGroups.ResourceGroup;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


@Data
@Entity(name = "clusters")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cluster {
    @Id
    private UUID id;
    @Column(unique = true)
    private String name;

    @ManyToOne
    @JsonBackReference
    private ResourceGroup resourceGroup;

    @OneToMany(mappedBy = "cluster", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<NodePool> nodePools;
}
