package com.itCar.base.api.user.controller;

import com.itCar.base.api.user.entity.Permission;
import com.itCar.base.api.user.service.PermissionService;
import com.itCar.base.config.result.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @ClassName: PermissionController 
 * @Description: TODO 权限 菜单管理
 * @author: liuzg
 * @Date: 2021/7/28 14:21
 * @Version: v1.0
 */
@RestController
@RequestMapping("/acl/permission")
@Api(tags = "权限：菜单管理")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public ResultBody indexAllPermission() {
        List<Permission> list = permissionService.queryAllMenuGuli();
        return ResultBody.success(list);
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping(value = "/remove/{id}")
    public ResultBody remove(@PathVariable String id) {
        permissionService.removeChildByIdGuli(id);
        return ResultBody.success("操作成功");
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping(value = "/doAssign")
    public ResultBody doAssign(String roleId, String[] permissionId) {
        permissionService.saveRolePermissionRealtionShipGuli(roleId, permissionId);
        return ResultBody.success("操作成功");
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping(value = "/toAssign/{roleId}")
    public ResultBody toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return ResultBody.success(list);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping(value = "/save")
    public ResultBody save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return ResultBody.success("操作成功");
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping(value = "/update")
    public ResultBody updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return ResultBody.success("操作成功");
    }

}
