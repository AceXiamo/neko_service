package com.xiamo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiamo.entity.MaConfig;
import com.xiamo.mapper.MaConfigMapper;
import com.xiamo.service.IMaConfigService;
import org.springframework.stereotype.Service;

/**
 * @Author: AceXiamo
 * @ClassName: MaConfigServiceImpl
 * @Date: 2023/3/30 17:14
 */
@Service
public class MaConfigServiceImpl extends ServiceImpl<MaConfigMapper, MaConfig> implements IMaConfigService {
}
