package com.itCar.base;

import com.itCar.base.config.bucket.BucketUtil;
import com.itCar.base.tools.InitSysLoad;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName: BasicApplication 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/23 10:35
 * @Version: v1.0
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
public class BasicApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(BasicApplication.class, args);

        // 为了方便测试这里定义1容量  1增长速率 、初始化将令牌桶塞满，启动后再每秒生成
//        BucketUtil bucketUtil = new BucketUtil(100, 1);
//         生成名为：bucket的令牌桶
//        BucketUtil.buckets.put("bucket", bucketUtil);

        log.info("项目启动成功、访问地址：http://127.0.0.1:" + InitSysLoad.PORT + "/" + "basicManageSys/doc.html");
        log.info("登录参数：{\"username\":\"admin\",\"password\":\"123456\",\"captcha\":\"\"}");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BasicApplication.class);
    }

}