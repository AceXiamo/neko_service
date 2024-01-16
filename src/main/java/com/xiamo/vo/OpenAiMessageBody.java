package com.xiamo.vo;

import lombok.Data;

/**
 * @Author: AceXiamo
 * @ClassName: OpenAiMessageBody
 * @Date: 2023/7/3 17:47
 */
@Data
public class OpenAiMessageBody {

    public OpenAiMessageBody() {}

    public OpenAiMessageBody(String role, String content) {
        this.role = role;
        this.content = content;
    }

    private String role;
    private String content;

}
