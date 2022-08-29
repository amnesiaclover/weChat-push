package com.itCar.base.api.quartz.job;

import com.itCar.base.api.quartz.services.JobService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: MyJob 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/19 14:26
 * @Version: v1.0
 */
public class MyJob implements Job {

    @Autowired
    private JobService service;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("MyJob："+service.sendEmail());
    }
}
