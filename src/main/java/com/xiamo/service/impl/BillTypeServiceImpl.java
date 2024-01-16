package com.xiamo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiamo.entity.BillType;
import com.xiamo.mapper.BillTypeMapper;
import com.xiamo.service.IBillTypeService;
import org.springframework.stereotype.Service;

/**
 * @Author: AceXiamo
 * @ClassName: BillTypeServiceImpl
 * @Date: 2023/7/8 11:57
 */
@Service
public class BillTypeServiceImpl extends ServiceImpl<BillTypeMapper, BillType> implements IBillTypeService {
}
