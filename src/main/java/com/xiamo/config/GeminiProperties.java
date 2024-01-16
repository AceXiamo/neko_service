package com.xiamo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: AceXiamo
 * @ClassName: GeminiProperties
 * @Date: 2023/12/19 23:41
 */
@Data
@Component
@ConfigurationProperties(prefix = "gemini")
public class GeminiProperties {

    private String key;

}
