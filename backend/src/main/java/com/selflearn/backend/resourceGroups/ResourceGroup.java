package com.selflearn.backend.resourceGroups;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.selflearn.backend.clusters.Cluster;
import com.selflearn.backend.subscriptions.Subscription;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Entity(name = "resource_groups")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String name;
    private String sha;

    @ManyToOne
    @JsonBackReference
    private Subscription subscription;

    @OneToMany(mappedBy = "resourceGroup", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Cluster> clusters;
}
