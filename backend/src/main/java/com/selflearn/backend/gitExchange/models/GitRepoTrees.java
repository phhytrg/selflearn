package com.selflearn.backend.gitExchange.models;

import lombok.Data;

import java.util.List;

@Data
public class GitRepoTrees {
    private String sha;
    private String url;
    private List<GitTreeNode> tree;
    private boolean truncated;
}