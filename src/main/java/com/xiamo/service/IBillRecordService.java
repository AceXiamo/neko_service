package com.xiamo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiamo.entity.BillRecord;
import com.xiamo.vo.BillMonthData;
import com.xiamo.vo.BillStatisticsData;
import com.xiamo.vo.BillTodayData;

/**
 * The interface Bill record service.
 *
 * @Author: AceXiamo
 * @ClassName: IBillRecordService
 * @Date: 2023 /7/3 16:43
 */
public interface IBillRecordService extends IService<BillRecord> {

    /**
     * List for month list.
     *
     * @param month the month
     * @return the list
     */
    BillMonthData listForMonth(String month);

    /**
     * Today data bill today data.
     *
     * @return the bill today data
     */
    BillTodayData todayData();


    /**
     * Statistics bill statistics data.
     *
     * @param date the date
     * @param type the type
     * @return the bill statistics data
     */
    BillStatisticsData statistics(String date);

}
