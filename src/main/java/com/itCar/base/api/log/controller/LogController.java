package com.itCar.base.api.log.controller;

import com.itCar.base.annotation.OperationLog;
import com.itCar.base.api.log.entity.MyLog;
import com.itCar.base.api.log.service.MyLogService;
import com.itCar.base.config.result.ResultBody;
import com.itCar.base.tools.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: LogController 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/28 16:27
 * @Version: v1.0
 */
@RestController
@RequestMapping(value = "/log")
@Api(tags = "系统：日志管理")
public class LogController {

    @Autowired
    private MyLogService service;


    @GetMapping(value = "/logList")
    @ApiOperation(value = "获取日志列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "logType", value = "日志类型", required = false, dataType = "String"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", defaultValue = "1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小", defaultValue = "10", required = true, dataType = "int")
    })
    public ResultBody<MyLog> logList(String logType, Integer currentPage, Integer pageSize) {
        List<MyLog> list = service.logList(logType, currentPage, pageSize);
        PageResult<MyLog> pageResult = new PageResult<>(list);
        return ResultBody.success(pageResult);
    }


//    @OperationLog(value = "删除日志", moduleName = "系统：日志管理")
    @DeleteMapping(value = "/delLog")
    @ApiOperation(value = "删除日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = false, dataType = "String"),
            @ApiImplicitParam(name = "logType", value = "日志类型", required = true, dataType = "String")
    })
    public ResultBody delLog(String id, String logType) {
        int log = service.delLog(id, logType);
        return ResultBody.success(200, "成功删除["+log+"]条");
    }

}
