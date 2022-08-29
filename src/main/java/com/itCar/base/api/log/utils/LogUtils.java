package com.itCar.base.api.log.utils;

import cn.hutool.http.useragent.Browser;
import cn.hutool.http.useragent.UserAgent;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName: LogUtils 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/28 16:16
 * @Version: v1.0
 */
public class LogUtils {

    private static final char SEPARATOR = '_';

    private static final String UNKNOWN = "unknown";

    /**
     * 获取ip地址
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String comma = ",";
        String localhost = "127.0.0.1";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }
        if (localhost.equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return ip;
    }

    /**
     * 获取堆栈信息
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }


}
