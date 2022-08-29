package com.itCar.base.api.wxApi.controller;

import com.itCar.base.api.wxApi.services.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: WxApiController 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/30 9:53
 * @Version: v1.0
 */
@Controller  // 只是请求地址，不需要返回数据 所以不需要加 RestController
@RequestMapping("/api/ucenter/wx")
@Api(tags = "微信扫码登录")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    // 为了测试这样写，正常在公司不需要这样写，直接将项目代码部署到域名服务器中就可以了(地址用域名服务器地址)
    @ApiOperation(value = "获取扫描人信息，添加数据")
    @GetMapping(value = "/callback")
    public String callback(String code, String state) {
        String jwtToken = memberService.getCallback(code, state);
        // 最后：返回首页面，通过路径传递token字符串
        return "redirect:http://localhost:3000?token=" + jwtToken;
    }


    @ApiOperation(value = "生成微信扫描二维码")
    @GetMapping(value = "/login")
    public String getWxCode() {
        String url = memberService.getWxCodeLogin();
        // 重定向到请求微信地址里面
        return "redirect:" + url;
    }
}
