package com.xiamo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author: AceXiamo
 * @ClassName: OpenAiProperties
 * @Date: 2023/7/3 17:36
 */
@Data
@Component
@ConfigurationProperties(prefix = "openai")
public class OpenAiProperties {

    private String key;
    private Boolean open;

}
