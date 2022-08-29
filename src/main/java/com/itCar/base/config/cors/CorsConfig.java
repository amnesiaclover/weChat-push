package com.itCar.base.config.cors;

import com.itCar.base.config.bucket.BucketInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName: CorsConfig 
 * @Description: TODO cors安全跨域配置
 * @author: liuzg
 * @Date: 2021/5/14 14:50
 * @Version: v1.0
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("PUT", "DELETE", "GET", "POST")
                        .allowedHeaders("*")
                        .exposedHeaders("access-control-allow-headers", "access-control-allow-methods",
                                "access-control-allow-origin", "access-control-max-age", "X-Frame-Options")
                        .allowCredentials(false).maxAge(3600);
            }
        };

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 令牌桶拦截器 添加拦截器并选择拦截路径
        registry.addInterceptor(bucketInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public BucketInterceptor bucketInterceptor() {
        return new BucketInterceptor();
    }

}
