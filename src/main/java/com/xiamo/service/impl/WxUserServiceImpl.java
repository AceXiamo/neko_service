package com.xiamo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiamo.entity.WxUser;
import com.xiamo.mapper.WxUserMapper;
import com.xiamo.service.IWxUserService;
import org.springframework.stereotype.Service;

/**
 * @Author: AceXiamo
 * @ClassName: WxUserServiceImpl
 * @Date: 2023/3/3 21:19
 */
@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements IWxUserService {
}
