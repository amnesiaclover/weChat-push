package com.itCar.base.config.result;

/**
 * @ClassName: BaseErrorInfoInterface 
 * @Description: TODO  定义基本错误信息
 * @author: liuzg
 * @Date: 2021/5/17 10:33
 * @Version: v1.0
 */
public interface BaseErrorInfoInterface {

    /** 错误码*/
    String getResultCode();

    /** 错误描述*/
    String getResultMsg();
}
