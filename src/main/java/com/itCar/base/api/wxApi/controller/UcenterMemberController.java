//package com.itCar.base.api.wxApi.controller;
//
//import com.itCar.base.api.wxApi.entity.UcenterMember;
//import com.itCar.base.api.wxApi.entity.UcenterMemberOrder;
//import com.itCar.base.api.wxApi.entity.vo.RegisterVo;
//import com.itCar.base.api.wxApi.services.UcenterMemberService;
//import com.itCar.base.config.result.ResultBody;
//import com.itCar.base.tools.DateUtil;
//import com.itCar.base.tools.JwtUtils;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @ClassName: UcenterMemberController 
// * @Description: TODO
// * @author: liuzg
// * @Date: 2021/7/30 10:10
// * @Version: v1.0
// */
//@RestController
//@RequestMapping("/ucenter/member")
//@Api(tags = "用户注册中心")
//public class UcenterMemberController {
//
//    @Autowired
//    private UcenterMemberService memberService;
//
//    @ApiOperation(value = "用户登录")
//    @PostMapping(value = "/login")
//    public ResultBody loginUser(@RequestBody UcenterMember member) {
//        // member对象封装手机号和密码
//        // 调用service方法实现登录
//        // 返回token值，使用jwt生成
//        String token = memberService.login(member);
//        return ResultBody.success(token, "成功登录、您的登录过期时间为：" + DateUtil.getDate(6), 200);
//    }
//
//
//    @ApiOperation(value = "用户注册")
//    @PostMapping(value = "/register")
//    public ResultBody registerUser(@RequestBody RegisterVo registerVo) {
//        memberService.register(registerVo);
//        return ResultBody.success("已成功注册");
//    }
//
//
//    @ApiOperation(value = "根据token获取用户信息")
//    @GetMapping(value = "/getMemberInfo")
//    public ResultBody getMemberInfo(HttpServletRequest request) {
//        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
//        String memberId = JwtUtils.getMemberIdByJwtToken(request);
//        //查询数据库根据用户id获取用户信息
//        UcenterMember member = memberService.getById(memberId);
//        return ResultBody.success(member);
//    }
//
//
//    //根据用户id获取用户信息
//    @ApiOperation(value = "根据用户id获取用户信息")
//    @PostMapping(value = "/getUserInfoOrder/{id}")
//    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {
//        UcenterMember member = memberService.getById(id);
//        //把member对象里面值复制给UcenterMemberOrder对象
//        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
//        BeanUtils.copyProperties(member,ucenterMemberOrder);
//        return ucenterMemberOrder;
//    }
//
//
//    @ApiOperation(value = "查询某一天注册人数")
//    @GetMapping(value = "/countRegister/{day}")
//    public ResultBody countRegister(@PathVariable String day) {
//        Integer count = memberService.countRegisterDay(day);
//        return ResultBody.success(count);
//    }
//
//}
