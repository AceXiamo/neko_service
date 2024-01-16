package com.xiamo.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * The type Bill record.
 */
@Data
public class BillRecord {
    /**
     * id
     */
    @TableId
    private String id;

    /**
     * emoji
     */
    @TableField("icon")
    private String icon;

    /**
     * 记账信息
     */
    @TableField("remark")
    private String remark;

    /**
     * 金额
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 记录类型( 0: 入账 1: 出账 )
     */
    @TableField("record_type")
    private Integer recordType;

    /**
     * ai的吐槽
     */
    @TableField("ai_say")
    private String aiSay;

    /**
     * 记录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("record_time")
    private Date recordTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("update_time")
    private Date updateTime;

    /**
     * 微信用户openId
     */
    @TableField("open_id")
    private String openId;

    /**
     * 分类id
     */
    @TableField("type_id")
    private Integer typeId;

    /**
     * 分类名称
     */
    @TableField("type_name")
    private String typeName;

    /**
     * 图片
     */
    @TableField("images")
    private String images;

}

