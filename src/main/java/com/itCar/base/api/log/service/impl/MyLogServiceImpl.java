package com.itCar.base.api.log.service.impl;

import com.github.pagehelper.PageHelper;
import com.itCar.base.api.log.entity.MyLog;
import com.itCar.base.api.log.mapper.LogMapper;
import com.itCar.base.api.log.service.MyLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @ClassName: MyLogServiceImpl 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/28 16:24
 * @Version: v1.0
 */
@Service
public class MyLogServiceImpl implements MyLogService {

    @Resource
    private LogMapper mapper;

    @Override
    public void addOperationLog(MyLog log) {
        mapper.add(log);
    }

    @Override
    public List<MyLog> logList(String logType, Integer currentPage, Integer pageSize) {
        if (null != currentPage && null != pageSize) {
            if (0 != currentPage && 0 != pageSize) {
                PageHelper.startPage(currentPage, pageSize);
            }
        }
        List<MyLog> list = mapper.logList(logType);
        return list;
    }

    @Override
    public int delLog(String id, String logType) {
        int i = mapper.delLog(id, logType);
        return i;
    }
}
