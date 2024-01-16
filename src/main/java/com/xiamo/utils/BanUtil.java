package com.xiamo.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Assert;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiamo.constant.CommonConstant;
import com.xiamo.entity.WxUser;
import com.xiamo.security.SecurityService;
import com.xiamo.service.IWxUserService;

import java.util.List;

/**
 * @Author: AceXiamo
 * @ClassName: BanUtil
 * @Date: 2023/4/2 12:26
 * @Desc: 封禁分为 永久封禁 / 非永久封禁，分别对应两个字段 is_ban / ban_end_time
 */
public class BanUtil {

    private static String REDIS_KEY = "";

    private static IWxUserService wxUserService;

    static {
        wxUserService = SpringUtil.getBean(IWxUserService.class);
    }

    public static void reload() {
        LambdaQueryWrapper<WxUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WxUser::getIsBan, CommonConstant.TRUE)
                .or()
                .gt(WxUser::getBanEndTime, DateTime.now());
        List<WxUser> list = wxUserService.list(wrapper);
    }

    public static void isBan() {
        LambdaQueryWrapper<WxUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WxUser::getOpenId, SecurityService.getWxUser().getOpenId());
        WxUser user = wxUserService.getOne(wrapper);
        Assert.isFalse(user.getIsBan().equals("1"), "你已被封禁 😇");
    }

}
