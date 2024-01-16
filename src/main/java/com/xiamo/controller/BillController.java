package com.xiamo.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.xiamo.common.AjaxResult;
import com.xiamo.entity.BillRecord;
import com.xiamo.security.SecurityService;
import com.xiamo.service.IBillRecordService;
import com.xiamo.service.IRequestHistoryService;
import com.xiamo.utils.GeminiUtil;
import com.xiamo.utils.OpenAiUtil;
import com.xiamo.vo.BillRequestVo;
import com.xiamo.vo.OpenAiMessageBody;
import com.xiamo.vo.OpenAiRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Bill controller.
 *
 * @Author: AceXiamo
 * @ClassName: BillController
 * @Date: 2023 /7/3 16:39
 */
@RestController
@RequestMapping("bill")
public class BillController {

    /**
     * The Bill record service.
     */
    @Autowired
    private IBillRecordService billRecordService;

    /**
     * The Request history service.
     */
    @Autowired
    private IRequestHistoryService requestHistoryService;

    /**
     * List for month ajax result.
     *
     * @param month the month
     * @return the ajax result
     */
    @PostMapping("listForMonth")
    public AjaxResult listForMonth(@RequestParam(name = "month") String month) {
        return AjaxResult.success(billRecordService.listForMonth(month));
    }

    /**
     * Today data ajax result.
     *
     * @return the ajax result
     */
    @PostMapping("todayData")
    public AjaxResult todayData() {
        return AjaxResult.success(billRecordService.todayData());
    }

    /**
     * Save ajax result.
     *
     * @param data the data
     * @return the ajax result
     */
    @PostMapping("save")
    public AjaxResult save(@RequestBody BillRequestVo data) {
        BillRecord record = new BillRecord();
        record.setId(IdUtil.objectId());
        DateTime now = new DateTime();
        if (data.getDay().equals(DateUtil.format(now, "yyyy-MM-dd"))) {
            record.setRecordTime(now);
        } else {
            record.setRecordTime(DateUtil.parse(data.getDay(), "yyyy-MM-dd"));
        }
        var res = GeminiUtil.say(data.getChat(), data.getHasAiSay());
        requestHistoryService.saveReqHis(Objects.requireNonNull(SecurityService.getWxUser()).getOpenId(), res.getBody(), JSON.toJSONString(res));
        BeanUtil.copyProperties(JSON.parseObject(res.getText(), BillRecord.class), record, CopyOptions.create().ignoreNullValue());
        record.setOpenId(SecurityService.getWxUser().getOpenId());
        record.setImages(JSON.toJSONString(data.getImages()));
        billRecordService.save(record);
        return AjaxResult.success(record);
    }

    /**
     * Update ajax result.
     *
     * @param record the record
     * @return the ajax result
     */
    @PostMapping("update")
    public AjaxResult update(@RequestBody BillRecord record) {
        record.setUpdateTime(new DateTime());
        billRecordService.updateById(record);
        return AjaxResult.success();
    }

    /**
     * Delete ajax result.
     *
     * @param id the id
     * @return the ajax result
     */
    @PostMapping("del")
    public AjaxResult delete(@RequestParam(name = "id") String id) {
        billRecordService.removeById(id);
        return AjaxResult.success();
    }

    /**
     * Statistics ajax result.
     *
     * @param date the date
     * @return the ajax result
     */
    @PostMapping("statistics")
    public AjaxResult statistics(@RequestParam(name = "date") String date) {
        return AjaxResult.success(billRecordService.statistics(date));
    }

}