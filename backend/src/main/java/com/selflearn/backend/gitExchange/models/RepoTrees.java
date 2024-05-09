package com.selflearn.backend.gitExchange.models;

import lombok.Data;

@Data
public class RepoTrees {
    private String sha;
    private String url;
    private TreeNode[] tree;
    private boolean truncated;
}
