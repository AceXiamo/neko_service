package com.xiamo.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiamo.entity.RequestHistory;
import com.xiamo.mapper.RequestHistoryMapper;
import com.xiamo.service.IRequestHistoryService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author: AceXiamo
 * @ClassName: RequestHistoryServiceImpl
 * @Date: 2023/3/12 14:27
 */
@Service
public class RequestHistoryServiceImpl extends ServiceImpl<RequestHistoryMapper, RequestHistory> implements IRequestHistoryService {

    @Async
    @Override
    public void saveReqHis(String openId, String req, String res) {
        RequestHistory his = new RequestHistory();
        his.setOpenId(openId);
        his.setMessageContent(req);
        his.setResultContent(req);
        save(his);
    }

}
