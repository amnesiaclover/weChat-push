package com.itCar.base.api.security.service;

import com.itCar.base.api.user.entity.User;
import com.itCar.base.api.user.service.PermissionService;
import com.itCar.base.api.user.service.UserService;
import com.itCar.base.api.security.entity.MyUser;
import com.itCar.base.api.security.entity.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: UserDetailsServiceImpl 
 * @Description: TODO 自定义userDetailsService 认证用户详情 需要查询数据库
 * @author: liuzg
 * @Date: 2021/7/27 10:46
 * @Version: v1.0
 */
@Slf4j
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;


    /**
     * 根据账号获取用户信息
     * @param username
     * @return org.springframework.security.core.userdetails.UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        User user = userService.selectByUsername(username);

        // 判断用户是否存在
        if (null == user){
            throw new UsernameNotFoundException("用户名不存在!");
        }

        // 返回UserDetails实现类
        MyUser curUser = new MyUser();
        BeanUtils.copyProperties(user,curUser);

        List<String> authorities = permissionService.selectPermissionValueByUserId(user.getId());
        SecurityUser securityUser = new SecurityUser(curUser);
        securityUser.setPermissionValueList(authorities);
        return securityUser;
    }
}
