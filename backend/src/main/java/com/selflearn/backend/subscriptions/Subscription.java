package com.selflearn.backend.subscriptions;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.selflearn.backend.resourceGroups.ResourceGroup;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "subscriptions")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String name;

    private String sha;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "subscription", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ResourceGroup> resourceGroups;
}
