package com.itCar.base.api.quartz.controller;

import com.itCar.base.annotation.BucketAnnotation;
import com.itCar.base.annotation.OperationLog;
import com.itCar.base.api.quartz.entity.QuartzEntity;
import com.itCar.base.api.quartz.entity.QuartzVo;
import com.itCar.base.api.quartz.services.JobService;
import com.itCar.base.config.result.ResultBody;
import com.itCar.base.tools.BaseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: JobController 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/20 11:07
 * @Version: v1.0
 */
@BucketAnnotation // 想被限流的方法上加上次注解
@RestController
@RequestMapping(value = "/itCarJob", produces = "application/json")
@Api(tags = "定时任务")
public class JobController extends BaseUtil {

    @Autowired
    private JobService service;

    @OperationLog(value = "添加/修改Job", moduleName = "定时任务")
    @PostMapping(value = "/addOrModifyJob")
    @ApiOperation(value = "添加/修改Job")
    public ResultBody addOrModifyJob(QuartzEntity quartz){
        return service.addJob(quartz);
    }

    @GetMapping(value = "/listJob")
    @ApiOperation(value = "获取Job列表")
    public ResultBody listJob(QuartzVo quartz, Integer pageNo, Integer pageSize, Integer export) throws SchedulerException {
        List<QuartzEntity> list = service.listQuartzEntity(quartz, pageNo, pageSize);
        if (1 == export){
            write(service.listJobExport(list));
            return null;
        }
        return ResultBody.success(list);
    }

    @OperationLog(value = "执行一次任务", moduleName = "定时任务")
    @PostMapping(value = "/trigger")
    @ApiOperation(value = "执行一次任务")
    public  ResultBody trigger(QuartzVo quartz) {
        ResultBody result = service.trigger(quartz);
        return result;
    }

    @OperationLog(value = "暂停Job", moduleName = "定时任务")
    @PostMapping(value = "/pause")
    @ApiOperation(value = "暂停Job")
    public  ResultBody pause(QuartzVo quartz) {
        ResultBody result = service.pause(quartz);
        return result;
    }

    @OperationLog(value = "恢复任务Job", moduleName = "定时任务")
    @PostMapping(value = "/resume")
    @ApiOperation(value = "恢复任务Job")
    public  ResultBody resume(QuartzVo quartz) {
        ResultBody result = service.resime(quartz);
        return result;
    }

    @OperationLog(value = "删除Job", moduleName = "定时任务")
    @PostMapping(value = "/remove")
    @ApiOperation(value = "删除Job")
    public ResultBody remove(QuartzVo quartz) {
        ResultBody result = service.remove(quartz);
        return result;
    }


}
