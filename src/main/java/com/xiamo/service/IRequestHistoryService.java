package com.xiamo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiamo.entity.RequestHistory;

/**
 * The interface Request history service.
 *
 * @Author: AceXiamo
 * @ClassName: IRequestHistoryService
 * @Date: 2023 /3/12 14:26
 */
public interface IRequestHistoryService extends IService<RequestHistory> {

    /**
     * Save req his.
     *
     * @param openId    the open id
     * @param userHisId the user his id
     * @param req       the req
     * @param res       the res
     */
    void saveReqHis(String openId, String req, String res);

}
