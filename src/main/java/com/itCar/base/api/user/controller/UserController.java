package com.itCar.base.api.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itCar.base.api.user.entity.User;
import com.itCar.base.api.user.service.IndexService;
import com.itCar.base.api.user.service.RoleService;
import com.itCar.base.api.user.service.UserService;
import com.itCar.base.config.result.ResultBody;
import com.itCar.base.tools.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserController 
 * @Description: TODO 用户表
 * @author: liuzg
 * @Date: 2021/7/28 14:28
 * @Version: v1.0
 */
@RestController
@RequestMapping("/acl/user")
@Api(tags = "权限：后端用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private IndexService indexService;


    @ApiOperation(value = "根据token获取用户信息")
    @GetMapping(value = "/info")
    public ResultBody info() {
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return ResultBody.success(userInfo);
    }

    @ApiOperation(value = "获取菜单")
    @GetMapping(value = "/menu")
    public ResultBody getMenu() {
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return ResultBody.success(permissionList);
    }

    @ApiOperation(value = "登出")
    @PostMapping(value = "/logout")
    public ResultBody logout() {
        return ResultBody.success("已安全退出");
    }

    @ApiOperation(value = "获取管理用户分页列表")
    @GetMapping(value = "/{page}/{limit}")
    public ResultBody index(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
                            @ApiParam(name = "courseQuery", value = "查询对象", required = false) User userQueryVo) {
        Page<User> pageParam = new Page<>(page, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(userQueryVo.getUsername())) {
            wrapper.like("username", userQueryVo.getUsername());
        }

        IPage<User> pageModel = userService.page(pageParam, wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("item", pageModel.getRecords());
        map.put("total", pageModel.getTotal());
        return ResultBody.success(map);
    }

    @ApiOperation(value = "新增管理用户")
    @PostMapping(value = "/save")
    public ResultBody save(@RequestBody User user) {
        user.setPassword(MD5.encrypt(user.getPassword()));
        userService.save(user);
        return ResultBody.success();
    }

    @ApiOperation(value = "修改管理用户")
    @PutMapping(value = "/update")
    public ResultBody updateById(@RequestBody User user) {
        if (user.getPassword()!=null)
            user.setPassword(MD5.encrypt(user.getPassword()));
        userService.updateById(user);
        return ResultBody.success("操作成功");
    }

    @ApiOperation(value = "删除管理用户")
    @DeleteMapping(value = "/remove/{id}")
    public ResultBody remove(@PathVariable String id) {
        userService.removeById(id);
        return ResultBody.success();
    }

    @ApiOperation(value = "根据id列表删除管理用户")
    @DeleteMapping(value = "/batchRemove")
    public ResultBody batchRemove(@RequestBody List<String> idList) {
        userService.removeByIds(idList);
        return ResultBody.success("操作成功");
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping(value = "/toAssign/{userId}")
    public ResultBody toAssign(@PathVariable String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return ResultBody.success(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping(value = "/doAssign")
    public ResultBody doAssign(@RequestParam String userId, @RequestParam String[] roleId) {
        roleService.saveUserRoleRealtionShip(userId, roleId);
        return ResultBody.success("操作成功");
    }
}
