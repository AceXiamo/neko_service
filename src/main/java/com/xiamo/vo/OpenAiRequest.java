package com.xiamo.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: AceXiamo
 * @ClassName: OpenAiRequest
 * @Date: 2023/7/3 17:45
 */
@Data
public class OpenAiRequest {

    public OpenAiRequest() {
        this.model = "gpt-3.5-turbo";
    }

    public OpenAiRequest(String model) {
        this.model = model;
    }

    private String model;
    private List<OpenAiMessageBody> messages;
}
