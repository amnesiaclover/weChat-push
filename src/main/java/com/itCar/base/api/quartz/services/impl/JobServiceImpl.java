package com.itCar.base.api.quartz.services.impl;

import com.itCar.base.api.quartz.entity.QuartzEntity;
import com.itCar.base.api.quartz.entity.QuartzVo;
import com.itCar.base.api.quartz.mapper.JobMapper;
import com.itCar.base.api.quartz.services.JobService;
import com.itCar.base.config.bucket.BucketUtil;
import com.itCar.base.config.exceptionhandler.BizException;
import com.itCar.base.config.result.ResultBody;
import com.itCar.base.tools.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: JobServiceImpl 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/20 10:40
 * @Version: v1.0
 */
@Slf4j
@Service(value = "service")
public class JobServiceImpl implements JobService {

    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    @Resource
    private JobMapper jobMapper;

    @Override
    public String resultStr() {
        return "**********************统计成功：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    @Override // 添加Job
    public ResultBody addJob(QuartzEntity quartz) {
        //获取Scheduler实例、废弃、使用自动注入的scheduler、否则spring的service将无法注入
        //Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        //如果是修改  展示旧的 任务
        try {
            if (quartz.getOldJobGroup() != null) {
                JobKey key = new JobKey(quartz.getOldJobName(), quartz.getOldJobGroup());
                scheduler.deleteJob(key);
            }
            Class cls = Class.forName(quartz.getJobClassName());
            cls.newInstance();
            //构建job信息
            JobDetail job = JobBuilder.newJob(cls)
                    .withIdentity(quartz.getJobName(), quartz.getJobGroup())
                    .withDescription(quartz.getDescription()).build();
            job.getJobDataMap().put("jobMethodName", quartz.getJobMethodName());
            // 触发时间点
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(quartz.getCronExpression());
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger" + quartz.getJobName(), quartz.getJobGroup())
                    .startNow()
                    .withSchedule(cronScheduleBuilder).build();
            //交由Scheduler安排触发
            scheduler.scheduleJob(job, trigger);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultBody.error("操作失败");
        }
        return ResultBody.success(200, "操作成功");
    }

    @Override
    public List<QuartzEntity> listQuartzEntity(QuartzVo quartz, Integer pageNo, Integer pageSize) throws SchedulerException {
        List<QuartzEntity> list = jobMapper.getQuartzJobList(quartz.getJobName());
        for (QuartzEntity quartzEntity : list) {
            JobKey key = new JobKey(quartzEntity.getJobName(), quartzEntity.getJobGroup());
            JobDetail jobDetail = scheduler.getJobDetail(key);
            if (jobDetail != null) {
                quartzEntity.setJobMethodName(jobDetail.getJobDataMap().getString("jobMethodName"));
            }
        }
        return list;
    }

    @Override
    public ResultBody trigger(QuartzVo quartz) {
        try {
            JobKey key = new JobKey(quartz.getJobName(), quartz.getJobGroup());
            scheduler.triggerJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return ResultBody.error("操作失败");
        }
        return ResultBody.success("操作成功");
    }

    @Override
    public ResultBody pause(QuartzVo quartz) {
        try {
            JobKey key = new JobKey(quartz.getJobName(), quartz.getJobGroup());
            scheduler.pauseJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return ResultBody.error("操作失败");
        }
        return ResultBody.success("操作成功");
    }

    @Override
    public ResultBody resime(QuartzVo quartz) {
        try {
            JobKey key = new JobKey(quartz.getJobName(), quartz.getJobGroup());
            scheduler.resumeJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return ResultBody.error("操作失败");
        }
        return ResultBody.success("操作成功");
    }

    @Override
    public ResultBody remove(QuartzVo quartz) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(quartz.getJobName(), quartz.getJobGroup());
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(quartz.getJobName(), quartz.getJobGroup()));
            System.out.println("removeJob:" + JobKey.jobKey(quartz.getJobName()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultBody.error("操作失败");
        }
        return ResultBody.success("操作成功");
    }

    @Override
    public String sendEmail() {
        return "发送邮件成功：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    @Override
    public String bucketTimer() {
        if (BucketUtil.buckets.containsKey("bucket")) {
            //名为：bucket的令牌桶 开始不断生成令牌
            BucketUtil.buckets.get("bucket").incrTokens();
        }
        return "当前令牌桶内存在：" + new BucketUtil().getSize() + "个令牌";
    }

    @Override
    public Workbook listJobExport(List<QuartzEntity> list) {
        if (0 == list.size()) {
            throw new BizException("job任务为空!");
        }
        Workbook workbook = null;
        InputStream stream = this.getClass().getResourceAsStream("/templates/job/jobList.xlsx");
        try {
            workbook = WorkbookFactory.create(stream);
        } catch (Exception e) {
            log.error("生成工作簿失败: " + e.getMessage());
            return null;
        }
        workbook.setSheetName(0, "job列表");
        Sheet sheet = workbook.getSheetAt(0);
        sheet.getRow(0).getCell(0).setCellValue("定时任务详情");
        ExcelUtil.copyRow(sheet, 2, list.size());
        ExcelUtil.replacel(sheet, list, 2);
        return workbook;
    }

}
