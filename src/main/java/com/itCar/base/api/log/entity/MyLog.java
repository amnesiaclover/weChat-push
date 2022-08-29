package com.itCar.base.api.log.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: MyLog 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/28 16:30
 * @Version: v1.0
 */
@Data
public class MyLog {


    @ApiModelProperty(value = "主键")
    private String id;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "IP地址")
    private String ip;
    @ApiModelProperty(value = "接口描述")
    private String description;
    @ApiModelProperty(value = "参数")
    private String params;
    @ApiModelProperty(value = "执行时间")
    private String executionTime;
    @ApiModelProperty(value = "接口名成")
    private String method;
    @ApiModelProperty(value = "模快名称")
    private String moduleName;
    @ApiModelProperty(value = "访问接口路径")
    private String doInterfacePath;
    @ApiModelProperty(value = "日志类型")
    private String type;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "堆栈信息")
    private String exceptionDetail;

}
