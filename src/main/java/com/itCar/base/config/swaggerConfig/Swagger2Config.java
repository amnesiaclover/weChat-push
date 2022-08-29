package com.itCar.base.config.swaggerConfig;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName: Swagger2Config 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/5/14 15:41
 * @Version: v1.0
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return  new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.itCar.base"))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("基础业务管理系统")
                .description("")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}
