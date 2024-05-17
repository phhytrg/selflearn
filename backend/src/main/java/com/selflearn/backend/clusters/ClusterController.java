package com.selflearn.backend.clusters;

import com.selflearn.backend.clusters.services.ClusterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/${apiVersion}/clusters")
public class ClusterController {
    private final ClusterService clusterService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<?> findClusters(
            @RequestParam(required = false, value = "resourceGroupName") String resourceGroupName,
            @RequestParam(required = false, value = "subscriptionName") String subscriptionName) {
        return ResponseEntity.ok(clusterService.getClusters(subscriptionName, resourceGroupName));
    }
}
