package com.itCar.base.api.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itCar.base.config.exceptionhandler.BizException;
import com.itCar.base.config.result.ResultBody;
import com.itCar.base.api.security.entity.MyUser;
import com.itCar.base.api.security.entity.SecurityUser;
import com.itCar.base.api.security.security.TokenManager;
import com.itCar.base.api.security.util.ResponseUtil;
import com.itCar.base.tools.DateUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * <p>
 * 登录过滤器，继承UsernamePasswordAuthenticationFilter，对用户名密码进行登录校验
 * 认证过滤器
 * </p>
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    // 登录请求路径
    String defaultFilterProcessUrl = "/admin/acl/login";

    public TokenLoginFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/acl/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            MyUser user = new ObjectMapper().readValue(req.getInputStream(), MyUser.class);
            this.validateCode(req, user.getCaptcha());
            System.out.println(user.getUsername()+"、"+user.getPassword());

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 登录成功
     *
     * @param req
     * @param res
     * @param chain
     * @param auth
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        SecurityUser user = (SecurityUser) auth.getPrincipal();
        String token = tokenManager.createToken(user.getCurrentMyUserInfo().getUsername());
        redisTemplate.opsForValue().set(user.getCurrentMyUserInfo().getUsername(), user.getPermissionValueList());
//        Map<String, Object> map = new HashMap<>();
//        map.put("token", token);
//        map.put("userinfo", user);
        ResponseUtil.out(res, ResultBody.success(token,
                "登录成功、失效时间为：" + DateUtil.getDate(6), 200));
    }

    /**
     * 登录失败
     *
     * @param request
     * @param response
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {
        System.out.println(e.getMessage());
        ResponseUtil.out(response, ResultBody.error("登录失败", 401));
    }

    /**
     * 图像验证码
     * @param req
     */
    public void validateCode(HttpServletRequest req, String captcha) {
        if ("POST".equalsIgnoreCase(req.getMethod()) && defaultFilterProcessUrl.equals(req.getServletPath())) {
            // 登录请求校验验证码，非登录请求不用校验
            HttpSession session = req.getSession();
//            String genCaptcha = (String) req.getSession().getAttribute("captcha");//验证码的信息存放在seesion中，具体看EasyCaptcha官方解释
            String genCaptcha = (String) redisTemplate.opsForValue().get("captcha");
            if ("".equals(genCaptcha)|| null == genCaptcha){
                this.resultMg(session, req, "未获取到session中验证信息");
            }
            if (StringUtils.isEmpty(captcha)) {
                this.resultMg(session, req, "验证码不能为空");
            }
            if (!genCaptcha.toLowerCase().equals(captcha.toLowerCase())) {
                this.resultMg(session, req, "验证码错误");
            }
//            session.removeAttribute("captcha");//删除缓存里的验证码信息
            redisTemplate.delete("captcha");
        }
    }

    public void resultMg(HttpSession session, HttpServletRequest req, String mg){
//        session.removeAttribute("captcha");//删除缓存里的验证码信息
        redisTemplate.delete("captcha");
        req.setAttribute("eM",mg);
        throw new BizException(mg);
    }


}
