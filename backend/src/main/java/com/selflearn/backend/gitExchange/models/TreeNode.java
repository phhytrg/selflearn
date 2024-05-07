package com.selflearn.backend.gitExchange.models;

import lombok.Data;

@Data
public class TreeNode {
    private String path;
    private String mode;
    private String type;
    private String sha;
    private String url;
}
