package com.itCar.base.api.log.mapper;

import com.itCar.base.api.log.entity.MyLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * @ClassName: LogMapper 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/28 16:23
 * @Version: v1.0
 */
@Mapper
public interface LogMapper {


    void add(MyLog log);

    List<MyLog> logList(String logType);

    int delLog(@P("id") String id, @Param("logType") String logType);
}
