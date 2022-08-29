package com.itCar.base.api.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itCar.base.api.user.entity.User;
import com.itCar.base.api.user.mapper.UserMapper;
import com.itCar.base.api.user.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @ClassName: UserServiceImpl 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/27 10:54
 * @Version: v1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }
}
