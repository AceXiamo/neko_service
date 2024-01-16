package com.xiamo.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@TableName("request_history")
public class RequestHistory {
    /**
     * Id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * openId
     */
    @TableField("open_id")
    private String openId;

    /**
     * 消息体
     */
    @TableField("message_content")
    private String messageContent;

    /**
     * 响应结果
     */
    @TableField("result_content")
    private String resultContent;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private Date createTime;
}

