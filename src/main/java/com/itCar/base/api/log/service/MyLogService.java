package com.itCar.base.api.log.service;


import com.itCar.base.api.log.entity.MyLog;

import java.util.List;

/**
 * @ClassName: MyLogService 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/28 16:26
 * @Version: v1.0
 */
public interface MyLogService {

    // 添加操作日志记录
    void addOperationLog(MyLog log);

    // 获取日志列表
    List<MyLog> logList(String logType, Integer currentPage, Integer pageSize);

    int delLog(String id, String logType);
}
