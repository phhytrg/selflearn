package com.selflearn.backend.clusters;

import com.selflearn.backend.clusters.services.ClusterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/${apiVersion}/clusters")
public class ClusterController {
    private final ClusterService clusterService;
    @GetMapping()
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(clusterService.fetchAll());
    }
}
