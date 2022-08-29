package com.itCar.base.api.quartz.job;

import com.itCar.base.api.quartz.services.JobService;
import com.itCar.base.tools.EmailUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: MyJob2 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/20 13:25
 * @Version: v1.0
 */
public class SendMailRegularly implements Job {
    @Autowired
    private JobService service;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println("SendMailRegularly：");
        Map<String, String> map = new HashMap<>();
        map.put("xxx@yeah.net", "to");
        String subject = "面试邀请";
        String context = "陈先生： 您好，这是测试邮件发送，点击下面url进行激活 http://www.baidu.com 如果不能跳转，请复制链接至浏览器 如果不是本人，请删除邮件";
        EmailUtil.sendMail(map, subject, context);
    }
}
