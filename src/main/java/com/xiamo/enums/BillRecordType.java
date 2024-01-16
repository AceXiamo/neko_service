package com.xiamo.enums;

import lombok.Getter;

/**
 * @Author: AceXiamo
 * @ClassName: BillRecordType
 * @Date: 2023/7/3 20:25
 */
@Getter
public enum BillRecordType {

    OUT(0, "出账"),
    IN(1, "入账")
    ;

    private Integer value;
    private String desc;

    BillRecordType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
