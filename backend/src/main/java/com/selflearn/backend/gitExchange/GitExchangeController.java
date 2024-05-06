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
    public ResponseEntity<?> getRepoTrees(String owner, String repo) {
        return ResponseEntity.ok(gitExchangeService.getRepoTrees(owner, repo));
    }
}
