package com.itCar.base.api.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itCar.base.api.user.entity.User;

/**
 * @ClassName: UserService 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/27 10:52
 * @Version: v1.0
 */
public interface UserService extends IService<User> {

    // 从数据库中取出用户信息
    User selectByUsername(String username);
}
