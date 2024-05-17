package com.selflearn.backend.gitExchange.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateTreeResponse extends GitBaseResponse {
    private boolean truncated;
    List<TreeNode> tree;
}
