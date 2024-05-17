package com.selflearn.backend.gitExchange.dtos;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class CreateBlobRequest {
    @Getter
    public static enum EncodingType {
        BASE64("base64"),
        UTF8("utf-8");

        private final String value;

        EncodingType(String value) {
            this.value = value;
        }
    }

    private String content;
    private EncodingType encoding;
}
