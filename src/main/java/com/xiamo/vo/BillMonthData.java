package com.xiamo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: AceXiamo
 * @ClassName: BillMonthData
 * @Date: 2023/7/3 16:46
 */
@Data
public class BillMonthData {

    public BillMonthData() {
        this.in = BigDecimal.ZERO;
        this.out = BigDecimal.ZERO;
    }

    private BigDecimal out;
    private BigDecimal in;
    private List<BillDayData> days;

}
