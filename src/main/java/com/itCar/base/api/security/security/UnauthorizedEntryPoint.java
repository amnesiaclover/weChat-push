package com.itCar.base.api.security.security;

import com.itCar.base.config.result.ResultBody;
import com.itCar.base.api.security.util.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * 未授权的统一处理方式
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        ResponseUtil.out(response, ResultBody.error(Optional.ofNullable(request.getAttribute("eM"))
                .orElse("用户登录超时或未登录!请重新登录").toString()));
    }
}
