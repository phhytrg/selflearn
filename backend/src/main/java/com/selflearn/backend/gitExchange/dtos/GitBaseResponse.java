package com.selflearn.backend.gitExchange.dtos;

import lombok.Data;

@Data
public class GitBaseResponse {
    protected String sha;
    protected String url;
}
