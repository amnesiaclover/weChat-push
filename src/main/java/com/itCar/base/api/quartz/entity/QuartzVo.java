package com.itCar.base.api.quartz.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: QuartzVo 
 * @Description: TODO 用于 job的执行、暂停、恢复、删除
 * @author: liuzg
 * @Date: 2021/7/20 13:59
 * @Version: v1.0
 */
@Data
public class QuartzVo {

    @ApiModelProperty(value = "任务名称")
    private String jobName;
    @ApiModelProperty(value = "任务分组")
    private String jobGroup;
}
