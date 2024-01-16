package com.xiamo.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: AceXiamo
 * @ClassName: OpenAiResponse
 * @Date: 2023/7/3 17:39
 */
@Data
public class OpenAiResponse {

    private String id;
    private String object;
    private Long created;
    private String model;
    private List<Choices> choices;
    private Usage usage;

    @Data
    public class Choices {
        private Integer index;
        private OpenAiMessageBody message;
        private String finish_reason;
    }

    @Data
    public class Usage {
        private Integer prompt_tokens;
        private Integer completion_tokens;
        private Integer total_tokens;
    }
}
