package com.selflearn.backend.nodePool;

import com.selflearn.backend.nodePool.services.NodePoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${apiPrefix}/${apiVersion}/node-pools")
@RequiredArgsConstructor
public class NodePoolController {
    private final NodePoolService nodePoolService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> getNodePools(
            @RequestParam(required = false, value = "resourceGroupName") String resourceGroupName,
            @RequestParam(required = false, value = "subscriptionName") String subscriptionName,
            @RequestParam(required = false, value = "clusterName") String clusterName
    ){
        return ResponseEntity.ok(nodePoolService.getNodePools(subscriptionName, resourceGroupName, clusterName));
    }
}
