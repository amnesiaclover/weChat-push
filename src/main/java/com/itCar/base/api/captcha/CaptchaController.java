package com.itCar.base.api.captcha;

import com.itCar.base.annotation.OperationLog;
import com.itCar.base.config.result.ResultBody;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;

/**
 * @ClassName: CaptchaController 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/27 9:48
 * @Version: v1.0
 */
@RestController
@RequestMapping(value = "/admin", produces = "application/json")
@Api(tags = "系统：登录")
public class CaptchaController {

    @Autowired
    private RedisTemplate redisTemplate;

    @OperationLog(value = "获取验证码", moduleName = "登录模快")
    @GetMapping(value = "/captcha")
    @ApiOperation(value = "获取验证码")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 直接使用CaptchaUtil输出验证码
//        CaptchaUtil.out(request, response);

        // 采用自定义输出验证码，并将captcha存储在redis中/
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 数字和大写字母(验证码类型-1)
//        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
//        // 设置字体
//        specCaptcha.setFont(new Font("Verdana", Captcha.FONT_5, 32));  // 有默认字体，可以不用设置
//        // 设置类型，纯数字、纯字母、字母数字混合
//        specCaptcha.setCharType(Captcha.TYPE_DEFAULT);

        // 算术类型(验证码类型-2)
        ArithmeticCaptcha specCaptcha = new ArithmeticCaptcha(130, 48);
        specCaptcha.setLen(3);  // 几位数运算，默认是两位
        specCaptcha.getArithmeticString();  // 获取运算的公式：3+2=?
        specCaptcha.text();  // 获取运算的结果：5


        // 验证码存入session
//        request.getSession().setAttribute("captcha", specCaptcha.text()/*.toLowerCase()*/);
        redisTemplate.opsForValue().set("captcha", specCaptcha.text());
        // 输出图片流
        specCaptcha.out(response.getOutputStream());
    }

    @OperationLog(value = "读取验证码", moduleName = "登录模快")
    @GetMapping(value = "/readAuthCode")
    @ApiOperation(value = "读取验证码")
    public ResultBody readAuthCode() {
        Boolean hasKey = redisTemplate.hasKey("captcha");
        if (!hasKey){
            return ResultBody.success("key值不存在");
        }
        return ResultBody.success(redisTemplate.opsForValue().get("captcha"));

    }


}
