package com.itCar.base.api.quartz.entity;

import lombok.Data;

/**
 * @ClassName: QuartzEntity 
 * @Description: TODO 任务类  用于job的新增、修改
 * @author: liuzg
 * @Date: 2021/7/20 10:39
 * @Version: v1.0
 */
@Data
public class QuartzEntity {

    private String jobName;//任务名称
    private String jobGroup;//任务分组
    private String description;//任务描述
    private String jobClassName;//执行类
    private String jobMethodName;//执行方法
    private String cronExpression;//执行时间
    private String triggerName;//触发器名称
    private String triggerState;//触发器状态

    private String oldJobName;//任务名称 用于修改
    private String oldJobGroup;//任务分组 用于修改

}
