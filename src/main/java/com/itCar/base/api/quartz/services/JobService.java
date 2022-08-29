package com.itCar.base.api.quartz.services;

import com.itCar.base.api.quartz.entity.QuartzEntity;
import com.itCar.base.api.quartz.entity.QuartzVo;
import com.itCar.base.config.result.ResultBody;
import org.apache.poi.ss.usermodel.Workbook;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * @ClassName: JobService 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/20 10:39
 * @Version: v1.0
 */
public interface JobService {

    // 测试
    String sendEmail();
    // 测试
    String resultStr();


    ResultBody addJob(QuartzEntity quartz);

    List<QuartzEntity> listQuartzEntity(QuartzVo quartz, Integer pageNo, Integer pageSize) throws SchedulerException;

    ResultBody trigger(QuartzVo quartz);

    ResultBody pause(QuartzVo quartz);

    ResultBody resime(QuartzVo quartz);

    ResultBody remove(QuartzVo quartz);

    String bucketTimer();

    //导出job列表
    Workbook listJobExport(List<QuartzEntity> list);
}
