package com.selflearn.backend.gitExchange.dtos;

import lombok.Data;

import java.util.List;

@Data
public class RepoTreesDto {
    private String sha;
    private String url;
    private List<TreeNodeDto> tree;
    private boolean truncated;
}
