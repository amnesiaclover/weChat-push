package com.itCar.base.api.user.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itCar.base.api.user.entity.Permission;

import java.util.List;

/**
 * @ClassName: PermissionService 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/27 10:56
 * @Version: v1.0
 */
public interface PermissionService extends IService<Permission> {

    //获取全部菜单
    List<Permission> queryAllMenu();

    //根据角色获取菜单
    List<Permission> selectAllMenu(String roleId);

    //给角色分配权限
    void saveRolePermissionRealtionShip(String roleId, String[] permissionId);

    //递归删除菜单
    void removeChildById(String id);

    //根据用户id获取用户菜单
    List<String> selectPermissionValueByUserId(String id);

    List<JSONObject> selectPermissionByUserId(String id);

    // ***********************  *******************************************

    //获取全部菜单（递归查询、构建成树形结构）
    List<Permission> queryAllMenuGuli();

    //递归删除菜单
    void removeChildByIdGuli(String id);

    //给角色分配权限
    void saveRolePermissionRealtionShipGuli(String roleId, String[] permissionId);
}
