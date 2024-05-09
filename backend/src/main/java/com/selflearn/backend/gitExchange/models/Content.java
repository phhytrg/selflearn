package com.selflearn.backend.gitExchange.models;

import lombok.Data;

@Data
public class Content {
    private String name;
    private String path;
    private String sha;
    private String size;
    private String url;
    private String html_url;
    private String git_url;
    private String download_url;
    private String type;
    private String content;
    private String encoding;
    private Links _links;
}
