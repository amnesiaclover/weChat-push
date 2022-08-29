package com.itCar.base.config.result;

/**
 * @ClassName: CommonEnum 
 * @Description: TODO 普通枚举
 * @author: liuzg
 * @Date: 2021/5/17 10:34
 * @Version: v1.0
 */
public enum CommonEnum implements BaseErrorInfoInterface {

    // 数据操作错误定义
    SUCCESS("200", "成功"),
    BODY_NOT_MATCH("400","请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH("401","请求的数字签名不匹配!"),
    NOT_FOUND("404", "未找到该资源!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    SERVER_BUSY("503","服务器正忙，请稍后再试!");

    /** 错误码 */
    private String resultCode;

    /** 错误描述 */
    private String resultMsg;

    CommonEnum(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public String getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }

}
