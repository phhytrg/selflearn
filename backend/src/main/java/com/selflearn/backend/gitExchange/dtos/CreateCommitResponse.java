package com.selflearn.backend.gitExchange.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateCommitResponse extends GitBaseResponse {
    private String node_id;
    private String html_url;
}
