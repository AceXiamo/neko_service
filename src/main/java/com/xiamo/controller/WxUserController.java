package com.xiamo.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiamo.common.AjaxResult;
import com.xiamo.entity.WxUser;
import com.xiamo.enums.MaEnum;
import com.xiamo.model.LoginUser;
import com.xiamo.security.SecurityService;
import com.xiamo.service.IWxUserService;
import com.xiamo.utils.BanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The type Wx user controller.
 *
 * @Author: AceXiamo
 * @ClassName: WxUserController
 * @Date: 2023 /3/3 22:08
 */
@Slf4j
@RestController
@RequestMapping("/wx")
public class WxUserController {

    /**
     * The Wx ma service.
     */
    @Autowired
    private WxMaService wxMaService;

    /**
     * The Wx user service.
     */
    @Autowired
    private IWxUserService wxUserService;

    /**
     * The Security service.
     */
    @Autowired
    private SecurityService securityService;

    /**
     * Login ajax result.
     *
     * @param code the code
     * @return the ajax result
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestParam(name = "code") String code) {
        wxMaService.switchover(MaEnum.AI.getAppId());
        WxUser user = null;
        try {
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            LambdaQueryWrapper<WxUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(WxUser::getOpenId, session.getOpenid());
            WxUser wxUser = wxUserService.getOne(wrapper);
            if (wxUser == null) {
                user = new WxUser();
                user.setOpenId(session.getOpenid());
                user.setNickname("用户233");
                user.setAvatar("");
                wxUserService.save(user);
            } else {
                user = wxUser;
            }
            user.setSessionKey(session.getSessionKey());
        } catch (Exception ignored) {
            log.error(ignored.getMessage());
        }
        LoginUser loginUser = new LoginUser(user.getOpenId(), "");
        loginUser.setWxUser(user);
        user.setToken(securityService.generateToken(loginUser));
        return AjaxResult.success(user);
    }

    /**
     * Update ajax result.
     *
     * @param user the user
     * @return the ajax result
     */
    @PostMapping("/update")
    public AjaxResult update(@RequestBody WxUser user) {
        BanUtil.isBan();

        Assert.isTrue(StrUtil.isNotEmpty(user.getOpenId()), "呃呃，操作失败");
        wxUserService.updateById(user);
        return AjaxResult.success();
    }

}
