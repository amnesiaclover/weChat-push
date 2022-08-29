package com.itCar.base.api.wxApi.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itCar.base.api.wxApi.entity.UcenterMember;
import com.itCar.base.api.wxApi.entity.vo.RegisterVo;

/**
 * @ClassName: UcenterMemberService 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/30 9:57
 * @Version: v1.0
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    // 登录的方法
    String login(UcenterMember member);

    // 用户注册
    void register(RegisterVo registerVo);

    // 生成微信扫描二维码
    String getWxCodeLogin();

    // 获取扫描人信息，添加数据
    String getCallback(String code, String state);

    // 根据openid判断
    UcenterMember getOpenIdMember(String openid);

    // 查询某一天注册人数
    Integer countRegisterDay(String day);
}