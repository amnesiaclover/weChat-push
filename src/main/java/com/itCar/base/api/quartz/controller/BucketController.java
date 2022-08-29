package com.itCar.base.api.quartz.controller;

import com.itCar.base.annotation.BucketAnnotation;
import com.itCar.base.config.bucket.BucketUtil;
import com.itCar.base.config.result.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: BucketController 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2022/6/29 9:25
 * @DAY_NAME_SHORT: 星期三
 * @Version: v1.0
 */
@Component
@RestController
@RequestMapping(value = "/bucket", produces = "application/json")
@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
@Api(tags = "令牌桶")
public class BucketController {

    @Value("${defaultMaxCount}")
    int DEFAULT_MAX_COUNT;
    @Value("${defaultCreateRate}")
    int DEFAULT_CREATE_RATE;

    // 定时生成令牌
    @Scheduled(fixedRate = 1000)
    public void timer() {
        boolean key = BucketUtil.buckets.containsKey("bucket");
        if (!key) {
            BucketUtil bucketUtil = new BucketUtil(DEFAULT_MAX_COUNT, DEFAULT_CREATE_RATE);
            BucketUtil.buckets.put("bucket", bucketUtil);
        }
        if (new BucketUtil().getSize() != DEFAULT_MAX_COUNT) {
            //名为：bucket的令牌桶 开始不断生成令牌
            BucketUtil.buckets.get("bucket").incrTokens();
        }
    }

    // 想被限流的方法上加上次注解
    @BucketAnnotation
    @ApiOperation(value = "获取当前令牌桶数量")
    @GetMapping(value = "/bucket")
    public ResultBody bucket() {
        int size = new BucketUtil().getSize();
        size = size + 1;
        return ResultBody.success("当前令牌桶存在：" +  + size + "个令牌");
    }


}
