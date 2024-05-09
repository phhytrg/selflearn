package com.selflearn.backend.gitExchange.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCommitRequest {
    private String message;
    private String tree;
    private String[] parents;
}
