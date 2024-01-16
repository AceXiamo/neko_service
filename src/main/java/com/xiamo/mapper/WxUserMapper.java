package com.xiamo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiamo.entity.WxUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: AceXiamo
 * @ClassName: WxUserMapper
 * @Date: 2023/3/3 21:18
 */
@Mapper
public interface WxUserMapper extends BaseMapper<WxUser> {
}
