package com.selflearn.backend.gitExchange.dtos;

import lombok.Data;

@Data
public class WebHookRequest {
    // Right now I only need ref
    private String ref;
    //
}
