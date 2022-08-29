package com.itCar.base.tools;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName: InitSysLoad 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/22 9:23
 * @Version: v1.0
 */
@Component
public class InitSysLoad implements InitializingBean {

    // ************  从配置yml中读取配置  ************
    @Value("${config.email.host}")
    private String host;

    @Value("${config.email.com}")
    private String com;

    @Value("${config.email.stmp}")
    private String stmp;

    @Value("${config.email.isItCertified}")
    private String isItCertified;

    @Value("${config.email.sender}")
    private String sender;

    @Value("${config.email.authorizationCode}")
    private String authorizationCode;

    @Value("${server.port}")
    private String port;


    // ********** 将读取到的配置赋值给静态变量 ***********
    // 配置区县编号
    public static String HOST;
    public static String COM;
    public static String STMP;
    public static String ISITCERTIFIED;
    public static String SENDER;
    public static String AUTHORIZATIONCODE;
    public static String PORT;

    @Override
    public void afterPropertiesSet() throws Exception {
        HOST = host;
        COM = com;
        STMP = stmp;
        ISITCERTIFIED = isItCertified;
        SENDER = sender;
        AUTHORIZATIONCODE = authorizationCode;
        PORT = port;
    }
}
