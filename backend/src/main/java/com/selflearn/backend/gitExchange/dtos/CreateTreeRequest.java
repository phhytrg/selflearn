package com.selflearn.backend.gitExchange.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTreeRequest {
    @Data
    @Builder
    public static class TreeNodeRequest {
        private String path;
        private String mode;
        private String type;
        private String sha;
    }
    private String base_tree;
    private TreeNodeRequest[] tree;
}
