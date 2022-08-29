package com.itCar.base.api.wxApi.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.itCar.base.api.wxApi.entity.UcenterMember;
import com.itCar.base.api.wxApi.entity.vo.RegisterVo;
import com.itCar.base.api.wxApi.mapper.UcenterMemberMapper;
import com.itCar.base.api.wxApi.services.UcenterMemberService;
import com.itCar.base.api.wxApi.utils.ConstantWxUtils;
import com.itCar.base.api.wxApi.utils.HttpClientUtils;
import com.itCar.base.config.exceptionhandler.BizException;
import com.itCar.base.tools.JwtUtils;
import com.itCar.base.tools.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @ClassName: UcenterMemberServiceImpl 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/30 9:58
 * @Version: v1.0
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(UcenterMember member) {
        // 获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        // 手机号和密码非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new BizException("401", "登录手机号和密码不能为空");
        }

        // 判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);

        // 判断查询对象是否为空
        if (mobileMember == null) {// 没有这个手机号
            throw new BizException("401", "手机号不存在");
        }
        // 判断密码
        // 因为存储到数据库密码肯定加密的
        // 把输入的密码进行加密，再和数据库密码进行比较
        // 加密方式 MD5
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new BizException("401", "密码错误");
        }

        // 判断用户是否禁用
        if (mobileMember.getIsDisabled()) {
            throw new BizException("401", "用户已被禁用，登录失败");
        }

        // 登录成功
        // 按照规则生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode(); //验证码
        String mobile = registerVo.getMobile(); //手机号
        String nickname = registerVo.getNickname(); //昵称
        String password = registerVo.getPassword(); //密码

        // 非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new BizException("403", "注册失败");
        }

        // 判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BizException("403", "手机号重复，注册失败");
        }

        //数据添加数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));//密码需要加密的
        member.setIsDisabled(false);//用户不禁用
        // 设置默认头像
        member.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80");
        baseMapper.insert(member);
    }

    @Override // 生成微信扫描二维码
    public String getWxCodeLogin() {
        // 微信开放平台授权baseUrl  %s相当于?代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        // 对redirect_url进行URLEncoder编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 设置%s里面值
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "guli"
        );

        return url;
    }

    @Override // 获取扫描人信息，添加数据
    public String getCallback(String code, String state) {
        String jwtToken = "";
        try {
            // 1 获取code值，临时票据，类似于验证码
            // 2 拿着code请求 微信固定的地址，得到两个值 accsess_token 和 openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            // 拼接三个参数 ：id  秘钥 和 code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );

            // 请求这个拼接好的地址，得到返回两个值 accsess_token 和 openid
            // 使用httpclient发送请求，得到返回结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessTokenInfo: " + accessTokenInfo);

            // 从accessTokenInfo字符串获取出来两个值 accsess_token 和 openid
            // 把accessTokenInfo字符串转换map集合，根据map里面key获取对应值
            // 使用json转换工具 Gson
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");

            // 把扫描人信息添加数据库里面
            // 判断数据表里面是否存在相同微信信息，根据openid判断
            UcenterMember member = this.getOpenIdMember(openid);
            if (member == null) { // memeber是空，表没有相同微信数据，进行添加
                // 3、 拿着得到accsess_token 和 openid，再去请求微信提供固定的地址，获取到扫描人信息
                // 访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                // 拼接两个参数
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                // 发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);
                // 获取返回userinfo字符串扫描人信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname"); // 昵称
//                String sex = (String) userInfoMap.get("sex");  // 性别 添加目前报错
                String headimgurl = (String) userInfoMap.get("headimgurl"); // 头像

                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
//                member.setSex(sex);
                member.setAvatar(headimgurl);
                baseMapper.insert(member);
            }

            // 使用jwt根据member对象生成token字符串
            jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            return jwtToken;
        }catch (Exception e){
            e.printStackTrace();
            throw new BizException("401","扫码登录失败");
        }

    }

    @Override  //根据openid判断
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override // 查询某一天注册人数
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
