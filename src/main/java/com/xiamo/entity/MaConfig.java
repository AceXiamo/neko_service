package com.xiamo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ma_config")
public class MaConfig {
    /**
     * key
     */
    @TableId("config_key")
    private String configKey;

    /**
     * 配置内容(JSON字符串)
     */
    @TableField("config_value")
    private String configValue;
}

