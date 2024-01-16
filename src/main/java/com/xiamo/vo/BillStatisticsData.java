package com.xiamo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: AceXiamo
 * @ClassName: BillStatisticsData
 * @Date: 2023/7/20 16:48
 */
@Data
public class BillStatisticsData {

    private BigDecimal in;
    private BigDecimal out;
    private List<BillTypeData> inDetail;
    private List<BillTypeData> outDetail;

}
