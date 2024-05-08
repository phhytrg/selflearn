package com.selflearn.backend.clusters;

import com.selflearn.backend.clusters.services.ClusterService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/${apiVersion}/clusters")
public class ClusterController {
    private final ClusterService clusterService;

    @GetMapping()
    public ResponseEntity<?> findClusters(
            @RequestParam(required = false, value = "clusterName") String clusterName,
            @RequestParam(required = false, value = "resourceGroupName") String resourceGroupName,
            @RequestParam(required = false, value = "subscriptionName") String subscriptionName) {
        return ResponseEntity.ok(clusterService.filter(subscriptionName, resourceGroupName, clusterName));
    }
}
