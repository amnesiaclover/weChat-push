package com.itCar.base.api.log.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @ClassName: RequestHolder 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/28 16:18
 * @Version: v1.0
 */
public class RequestHolder {


    /**
     * 获取HttpServletRequest对象
     *
     * @return
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

}
