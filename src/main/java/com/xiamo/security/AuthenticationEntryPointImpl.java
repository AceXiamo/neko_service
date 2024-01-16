package com.xiamo.security;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson2.JSON;
import com.xiamo.common.AjaxResult;
import com.xiamo.utils.ServletUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: AceXiamo
 * @ClassName: AuthenticationEntryPointImpl
 * @Date: 2023/3/3 22:33
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException
    {
        int code = HttpStatus.HTTP_UNAUTHORIZED;
        String msg = StrUtil.format("请求访问：{}，认证失败", request.getRequestURI());
        ServletUtil.renderString(response, JSON.toJSONString(AjaxResult.error(code, msg)));
    }

}
