package com.itCar.base.api.quartz.job;

import com.itCar.base.api.quartz.services.JobService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: BucketTimer 
 * @Description: TODO 令牌桶定时
 * @author: liuzg
 * @Date: 2022/6/28 16:25
 * @DAY_NAME_SHORT: 星期二
 * @Version: v1.0
 */
public class BucketTimer implements Job {

    @Autowired
    private JobService service;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("令牌生成：" + service.bucketTimer());
    }
}
