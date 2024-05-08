package com.selflearn.backend.gitExchange;

import com.selflearn.backend.gitExchange.services.GitExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("${apiPrefix}/${apiVersion}/gitProjectExchange")
@RequiredArgsConstructor
public class GitExchangeController {
    private final GitExchangeService gitExchangeService;

    @GetMapping("/trees")
    public ResponseEntity<?> getRepoTrees() {
        return ResponseEntity.ok(gitExchangeService.getRepoTrees());
    }

    @GetMapping("/clusters/content")
    public ResponseEntity<?> getContent(String path) {
        return ResponseEntity.ok(gitExchangeService.getContent(path));
    }

    @GetMapping("/clusters")
    public ResponseEntity<?> getClusters(
            @RequestParam(required = false) String subscriptionName,
            @RequestParam(required = false) String resourceGroupName,
            @RequestParam(required = false) String clusterName) {
        return ResponseEntity.ok(gitExchangeService.getClusters(subscriptionName, resourceGroupName, clusterName));
    }

    @GetMapping("/sync")
    public ResponseEntity<?> sync() {
        return ResponseEntity.ok(gitExchangeService.syncWithDatabase());
    }

    @GetMapping("/subscriptions")
    public ResponseEntity<?> getSubscriptions() {
        return ResponseEntity.ok(
                gitExchangeService.getSubscriptions().stream().map(sub -> new HashMap<>() {{
                    put("name", sub);
                }}));
    }

    @GetMapping("/resource-groups")
    public ResponseEntity<?> getResourceGroups(@RequestParam(required = false) String subscriptionName) {
        return ResponseEntity.ok(gitExchangeService.getResourceGroups(subscriptionName).stream().map(resourceGroup -> new HashMap<>() {{
            put("name", resourceGroup);
        }}));
    }
}
