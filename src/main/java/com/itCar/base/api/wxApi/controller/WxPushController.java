package com.itCar.base.api.wxApi.controller;

import com.itCar.base.api.wxApi.services.WxPushService;
import com.itCar.base.config.result.ResultBody;
import com.itCar.base.tools.HttpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName: WxPushController 
 * @Description: TODO weiChat Push
 * @author: liuzg
 * @Date: 2022/8/29 9:47
 * @Week: 星期一
 * @Version: v1.0
 */
@Slf4j
@RestController
@RequestMapping("/admin/wxPush")
@Api(tags = "微信：微信推送")
public class WxPushController {

    @Value("${wx.config.appId}")
    private String appId;
    @Value("${wx.config.appSecret}")
    private String appSecret;


    private String accessToken = "";

    @Resource
    private WxPushService wxService;

    /**
     * 获取Token
     * 每天早上7：30执行推送
     *
     * @return
     */
    @Scheduled(cron = "0 30 7 ? * *")
    @ApiOperation(value = "获取weChatToken并执行推送")
    @GetMapping("/getAccessToken")
    public ResultBody getAccessToken() {
        //这里直接写死就可以，不用改，用法可以去看api
        String grant_type = "client_credential";
        //封装请求数据
        String params = "grant_type=" + grant_type + "&secret=" + appSecret + "&appid=" + appId;
        //发送GET请求
        String sendGet = HttpUtil.sendGet("https://api.weixin.qq.com/cgi-bin/token", params);
        // 解析相应内容（转换成json对象）
        com.alibaba.fastjson.JSONObject jsonObject1 = com.alibaba.fastjson.JSONObject.parseObject(sendGet);
        log.info("微信token响应结果=" + jsonObject1);
        //拿到accesstoken
        accessToken = (String) jsonObject1.get("access_token");
        return wxService.sendWeChatMsg(accessToken);
    }

}
