package com.selflearn.backend.resourceGroups;

import com.selflearn.backend.resourceGroups.services.ResourceGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("${apiPrefix}/${apiVersion}/resource-groups")
@RequiredArgsConstructor
public class ResourceGroupController {
    private final ResourceGroupService resourceGroupService;

    @GetMapping()
    public ResponseEntity<?> getAllResourceGroupsBySubscriptionName(@RequestParam(required = false) String subscriptionName) {
        return ResponseEntity.ok(resourceGroupService.fetchAllBySubscriptionId(subscriptionName));
    }
}
