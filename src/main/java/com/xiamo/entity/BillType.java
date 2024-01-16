package com.xiamo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class BillType {
    /**
     * id
     */
    @TableId
    private Integer id;

    /**
     * 类型( 0: 支出 1: 收入 )
     */
    @TableField("record_type")
    private Integer recordType;

    /**
     * 分类名称
     */
    @TableField("name")
    private String name;
}

