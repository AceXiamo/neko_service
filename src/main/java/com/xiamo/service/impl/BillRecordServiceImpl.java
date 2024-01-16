package com.xiamo.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiamo.entity.BillRecord;
import com.xiamo.enums.BillRecordType;
import com.xiamo.mapper.BillRecordMapper;
import com.xiamo.security.SecurityService;
import com.xiamo.service.IBillRecordService;
import com.xiamo.vo.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

/**
 * The type Bill record service.
 *
 * @Author: AceXiamo
 * @ClassName: BillRecordServiceImpl
 * @Date: 2023 /7/3 16:43
 */
@Service
public class BillRecordServiceImpl extends ServiceImpl<BillRecordMapper, BillRecord> implements IBillRecordService {

    @Override
    public BillMonthData listForMonth(String month) {
        BillMonthData monthData = new BillMonthData();

        LambdaQueryWrapper<BillRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BillRecord::getOpenId, SecurityService.getWxUser().getOpenId());
        wrapper.likeRight(BillRecord::getRecordTime, month);
        List<BillRecord> list = list(wrapper);
        List<BillDayData> res = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (BillRecord billRecord : list) {
                if (billRecord.getRecordType().equals(BillRecordType.IN.getValue())) {
                    monthData.setIn(monthData.getIn().add(billRecord.getPrice()));
                } else if (billRecord.getRecordType().equals(BillRecordType.OUT.getValue())) {
                    monthData.setOut(monthData.getOut().add(billRecord.getPrice()));
                }

                String day = DateUtil.format(billRecord.getRecordTime(), "yyyy-MM-dd");
                BillDayData data = res
                        .stream()
                        .filter(v -> v.getDay().equals(day))
                        .findFirst()
                        .orElse(null);
                if (data == null) {
                    data = new BillDayData(day);
                    res.add(data);
                }

                data.getDetails().add(billRecord);
                data.setDetails(data.getDetails()
                        .stream()
                        .sorted(Comparator.comparing(BillRecord::getRecordTime).reversed())
                        .collect(Collectors.toList()));

                if (billRecord.getRecordType().equals(BillRecordType.IN.getValue())) {
                    data.setIn(data.getIn().add(billRecord.getPrice()));
                } else if (billRecord.getRecordType().equals(BillRecordType.OUT.getValue())) {
                    data.setOut(data.getOut().add(billRecord.getPrice()));
                }
            }
        }


        monthData.setDays(res.stream().sorted((o1, o2) -> DateUtil.parse(o2.getDay(), "yyyy-MM-dd")
                .compareTo(DateUtil.parse(o1.getDay(), "yyyy-MM-dd"))).collect(Collectors.toList()));
        return monthData;
    }

    @Override
    public BillTodayData todayData() {
        BillTodayData todayData = new BillTodayData();
        LambdaQueryWrapper<BillRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BillRecord::getOpenId, SecurityService.getWxUser().getOpenId());
        DateTime now = DateTime.now();
        String today = now.toString("yyyy-MM-dd");
        String begin = DateUtil.beginOfDay(DateUtil.offsetDay(now, -1)).toString("yyyy-MM-dd HH:mm:ss");
        String end = DateUtil.endOfDay(now).toString("yyyy-MM-dd HH:mm:ss");
        wrapper.between(BillRecord::getRecordTime, begin, end);
        List<BillRecord> list = list(wrapper);

        BigDecimal lastDay = new BigDecimal(0);
        if (list != null && list.size() > 0) {
            for (BillRecord billRecord : list) {
                String day = DateTime.of(billRecord.getRecordTime()).toString("yyyy-MM-dd");
                if (day.equals(today)) {
                    if (billRecord.getRecordType().equals(BillRecordType.IN.getValue())) {
                        todayData.setIn(todayData.getIn().add(billRecord.getPrice()));
                    } else if (billRecord.getRecordType().equals(BillRecordType.OUT.getValue())) {
                        todayData.setOut(todayData.getOut().add(billRecord.getPrice()));
                    }
                } else {
                    if (billRecord.getRecordType().equals(BillRecordType.OUT.getValue())) {
                        lastDay = lastDay.add(billRecord.getPrice());
                    }
                }
            }
        }

        todayData.setWithLastDay(todayData.getOut().subtract(lastDay));
        return todayData;
    }

    @Override
    public BillStatisticsData statistics(String date) {
        BillStatisticsData result = new BillStatisticsData();
        sumDataHandle(date, result);
        typeDataHandle(date, result);
        return result;
    }

    /**
     * Sum data handle.
     *
     * @param date   the date
     * @param result the result
     */
    private void sumDataHandle(String date, BillStatisticsData result) {
        QueryWrapper<BillRecord> wrapper = new QueryWrapper<>();
        wrapper.select("sum(price) as price", "record_type");
        wrapper.eq("open_id", SecurityService.getWxUser().getOpenId());
        wrapper.likeRight("record_time", date);
        wrapper.groupBy("record_type");
        List<BillRecord> list = list(wrapper);

        result.setIn(list.stream().filter(v -> v.getRecordType().equals(BillRecordType.IN.getValue())).findFirst().orElse(new BillRecord()).getPrice());
        result.setOut(list.stream().filter(v -> v.getRecordType().equals(BillRecordType.OUT.getValue())).findFirst().orElse(new BillRecord()).getPrice());
    }

    /**
     * Type data handle.
     *
     * @param date   the date
     * @param result the result
     */
    private void typeDataHandle(String date, BillStatisticsData result) {
        QueryWrapper<BillRecord> wrapper = new QueryWrapper<>();
        wrapper.select("type_id", "type_name", "record_type", "sum(price) as price");
        wrapper.eq("open_id", SecurityService.getWxUser().getOpenId());
        wrapper.likeRight("record_time", date);
        wrapper.groupBy("type_id", "type_name");
        wrapper.orderByDesc("price");

        List<BillRecord> list = list(wrapper);
        List<BillTypeData> in = new ArrayList<>();
        List<BillTypeData> out = new ArrayList<>();
        for (BillRecord record : list) {
            if (BillRecordType.IN.getValue().equals(record.getRecordType())){
                in.add(new BillTypeData(record.getTypeId(), record.getTypeName(), record.getPrice()));
            } else if (BillRecordType.OUT.getValue().equals(record.getRecordType())) {
                out.add(new BillTypeData(record.getTypeId(), record.getTypeName(), record.getPrice()));
            }
        }

        result.setInDetail(in);
        result.setOutDetail(out);
    }
}
