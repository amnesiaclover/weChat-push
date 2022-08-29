package com.itCar.base.api.quartz.mapper;

import com.itCar.base.api.quartz.entity.QuartzEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName: JobMapper 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/23 13:21
 * @Version: v1.0
 */
@Mapper
public interface JobMapper {

    /**
     * 查询所有的job
     *
     * @param name
     * @return
     */
    List<QuartzEntity> getQuartzJobList(String name);
}
