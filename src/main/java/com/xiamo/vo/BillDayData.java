package com.xiamo.vo;

import com.xiamo.entity.BillRecord;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: AceXiamo
 * @ClassName: BillMonthData
 * @Date: 2023/7/3 16:46
 */
@Data
public class BillDayData {

    public BillDayData(String day) {
        this.day = day;
        this.in = BigDecimal.ZERO;
        this.out = BigDecimal.ZERO;
        this.details = new ArrayList<>();
    }

    private BigDecimal in;
    private BigDecimal out;
    private String day;
    private List<BillRecord> details;

}
