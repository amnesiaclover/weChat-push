package com.itCar.base.tools;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @ClassName: JwtUtils 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/3/25 15:55
 * @Version: v1.0
 */
public class JwtUtils {

    // 设置token过期时间
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    // App秘钥、加密操作
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    // 生成token字符串操作
    public static String getJwtToken(String id, String name){

        String JwtToken = Jwts.builder()
                // 第一部分 设置jwt头部信息 固定写法
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")

                // 设置过期时间
                .setSubject("basic-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))

                // 第二部分 主体不部分
                // 设置token主体部分，存储用户信息
                .claim("id", id)
                .claim("name", name)

                // 第三部分
                // 签名哈希 根据秘钥做编码，防伪标志
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            // 使用jwt判断token是否有效，有true无catch false
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token字符串获取用户信息(会员id)
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return "";
        // 解析token 拿到Claims
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        // 通过claims拿到加密前存储的主体信息
        Claims claims = claimsJws.getBody();
        // 通过get("id") 取到
        return (String)claims.get("id");
    }



}
