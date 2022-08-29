//package com.itCar.base.api.user.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.itCar.base.api.user.service.IndexService;
//import com.itCar.base.config.result.ResultBody;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * @ClassName: IndexController 
// * @Description: TODO
// * @author: liuzg
// * @Date: 2021/7/28 11:07
// * @Version: v1.0
// */
//@RestController
//@RequestMapping("/acl/index")
//@Api(tags = "")
//public class IndexController {
//
//    @Autowired
//    private IndexService indexService;
//
//    /**
//     * 根据token获取用户信息
//     */
//    @ApiOperation(value = "根据token获取用户信息")
//    @GetMapping(value = "/info")
//    public ResultBody info() {
//        //获取当前登录用户用户名
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Map<String, Object> userInfo = indexService.getUserInfo(username);
//        return ResultBody.success(userInfo);
//    }
//
//    /**
//     * 获取菜单
//     *
//     * @return
//     */
//    @ApiOperation(value = "获取菜单")
//    @GetMapping(value = "/menu")
//    public ResultBody getMenu() {
//        //获取当前登录用户用户名
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        List<JSONObject> permissionList = indexService.getMenu(username);
//        return ResultBody.success(permissionList);
//    }
//
//    @ApiOperation(value = "登出")
//    @PostMapping(value = "/logout")
//    public ResultBody logout() {
//        return ResultBody.success();
//    }
//
//}
