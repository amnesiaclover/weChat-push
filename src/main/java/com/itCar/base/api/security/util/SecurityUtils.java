package com.itCar.base.api.security.util;

import com.itCar.base.config.exceptionhandler.BizException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * @ClassName: SecurityUtils 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/28 16:15
 * @Version: v1.0
 */
public class SecurityUtils {

    /**
     * 获取系统用户名称
     *
     * @return 系统用户名称
     */
    public static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BizException("401", "当前登录状态过期");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    /**
     * 取得当前用户登录IP, 如果当前用户未登录则返回空字符串.
     * 此方法无用
     */
    public static String getCurrentUserIp() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new BizException("401", "当前登录状态过期");
        }
        Object details = authentication.getDetails();
        if (!(details instanceof WebAuthenticationDetails)) {
            return "";
        }
        WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
        return webDetails.getRemoteAddress();
    }

}
