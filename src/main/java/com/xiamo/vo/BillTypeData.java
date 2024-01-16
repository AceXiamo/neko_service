package com.xiamo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * The type Bill type data.
 *
 * @Author: AceXiamo
 * @ClassName: BillTypeData
 * @Date: 2023 /7/20 16:49
 */
@Data
public class BillTypeData {

    public BillTypeData() {}

    public BillTypeData(Integer typeId, String typeName, BigDecimal price) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.price = price;
    }

    /**
     * The Type id.
     */
    private Integer typeId;

    /**
     * The Type name.
     */
    private String typeName;

    /**
     * The Price.
     */
    private BigDecimal price;

}
