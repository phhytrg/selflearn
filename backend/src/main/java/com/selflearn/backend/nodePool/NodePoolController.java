package com.selflearn.backend.nodePool;

import com.selflearn.backend.nodePool.dtos.CreateNodePoolsRequest;
import com.selflearn.backend.nodePool.services.NodePoolService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

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
    ) {
        return ResponseEntity.ok(nodePoolService.getNodePools(subscriptionName, resourceGroupName, clusterName));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(String subscriptionName, String resourceGroupName, String clusterName) {
        return ResponseEntity.ok(nodePoolService.delete(subscriptionName, resourceGroupName, clusterName));
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> upload(CreateNodePoolsRequest request,
                                    @Parameter(
                                            description = "Files to be uploaded",
                                            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)  // Won't work without OCTET_STREAM as the mediaType.
                                    )
                                    @RequestPart
                                    MultipartFile file) {
        return ResponseEntity.ok(nodePoolService.uploadNodePools(request, file));
    }

    @PostMapping(value = "/upload2", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> upload2(@Parameter(
            description = "Files to be uploaded",
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
                                     @RequestPart(value = "file") MultipartFile[] files) {
        return ResponseEntity.ok().build();
    }
}
