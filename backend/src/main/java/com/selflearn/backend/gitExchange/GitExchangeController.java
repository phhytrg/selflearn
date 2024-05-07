package com.selflearn.backend.gitExchange;

import com.selflearn.backend.gitExchange.services.GitExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/clusters/all")
    public ResponseEntity<?> getAllClusters(){
        return ResponseEntity.ok(gitExchangeService.getAllCluster());
    }
}
