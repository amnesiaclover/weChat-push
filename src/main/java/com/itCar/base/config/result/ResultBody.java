package com.itCar.base.config.result;


import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: ResultBody 
 * @Description: TODO  返回结果体
 * @author: liuzg
 * @Date: 2021/5/17 10:43
 * @Version: v1.0
 */
public class ResultBody<T> {
    /**
     * 响应代码
     */
    private String code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应结果
     */
    private Object data;

    // 无参
    public ResultBody() {
    }
    // 只需要传递 code, msg
    public ResultBody(String code, String msg){
        this.code = code;
        this.msg = msg;
    }
    // 需要传递 code, msg, data
    public ResultBody(String code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    // 自定义 错误信息
    public ResultBody(BaseErrorInfoInterface errorInfo) {
        this.code = errorInfo.getResultCode();
        this.msg = errorInfo.getResultMsg();
    }

    // 成功可调方法
    public static ResultBody success() {
        return success(null);
    }
    // 自定义 code, msg
    public static ResultBody success(int code, String msg) {
        ResultBody rb = new ResultBody();
        rb.setCode(String.valueOf(code));
        rb.setMsg(msg);
        return rb;
    }
    public static ResultBody success(String msg) {
        ResultBody rb = new ResultBody();
        rb.setCode("200");
        rb.setMsg(msg);
        return rb;
    }
    public static ResultBody success(Object data, String msg, Integer code) {
        ResultBody rb = new ResultBody();
        rb.setCode(String.valueOf(code));
        rb.setMsg(msg);
        rb.setData(data);
        return rb;
    }
    // 只传 返回data
    public static ResultBody success(Object data) {
        ResultBody rb = new ResultBody();
        rb.setCode(CommonEnum.SUCCESS.getResultCode());
        rb.setMsg(CommonEnum.SUCCESS.getResultMsg());
        rb.setData(data);
        return rb;
    }

    // 失败 自定义错误信息
    public static ResultBody error(BaseErrorInfoInterface errorInfo) {
        ResultBody rb = new ResultBody();
        rb.setCode(errorInfo.getResultCode());
        rb.setMsg(errorInfo.getResultMsg());
        rb.setData(null);
        return rb;
    }
    // 传递错误 code, msg
    public static ResultBody error(String code, String message) {
        ResultBody rb = new ResultBody();
        rb.setCode(code);
        rb.setMsg(message);
        rb.setData(null);
        return rb;
    }
    // 只传递错误 msg(基本业务异常返回)
    public static ResultBody error(String message) {
        ResultBody rb = new ResultBody();
        rb.setCode("-1");
        rb.setMsg(message);
        rb.setData(null);
        return rb;
    }
    public static ResultBody error(String message, Integer code) {
        ResultBody rb = new ResultBody();
        rb.setCode(String.valueOf(code));
        rb.setMsg(message);
        rb.setData(null);
        return rb;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }



    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }


}
