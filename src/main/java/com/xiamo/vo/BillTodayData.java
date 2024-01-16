package com.xiamo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: AceXiamo
 * @ClassName: BillTodayData
 * @Date: 2023/7/5 21:15
 */
@Data
public class BillTodayData {

    public BillTodayData() {
        this.in = BigDecimal.ZERO;
        this.out = BigDecimal.ZERO;
        this.withLastDay = BigDecimal.ZERO;
    }

    private BigDecimal in;
    private BigDecimal out;
    private BigDecimal withLastDay;

}
