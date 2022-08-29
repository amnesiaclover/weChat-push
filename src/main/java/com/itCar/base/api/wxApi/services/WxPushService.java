package com.itCar.base.api.wxApi.services;

import com.itCar.base.config.result.ResultBody;

/**
 * @ClassName: WxPushService 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2022/8/29 9:50
 * @Week: 星期一
 * @Version: v1.0
 */
public interface WxPushService {

    ResultBody sendWeChatMsg(String accessToken);
}
