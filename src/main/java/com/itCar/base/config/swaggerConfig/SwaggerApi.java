package com.itCar.base.config.swaggerConfig;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Sets;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.Operation;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingScannerPlugin;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;

import java.util.Arrays;
import java.util.List;

@Component
public class SwaggerApi implements ApiListingScannerPlugin {


    /**
     * 实现此方法可手动添加ApiDescriptions
     *
     * @param context - Documentation context that can be used infer documentation context
     * @return List of {@link ApiDescription}
     * @see ApiDescription
     */
    @Override
    public List<ApiDescription> apply(DocumentationContext context) {
        Operation loginOperation = new OperationBuilder(new CachingOperationNameGenerator())
                .method(HttpMethod.POST)
                .summary("登录")
                .notes("用户登录认证")
                // 接收参数格式
                .consumes(Sets.newHashSet(MediaType.MULTIPART_FORM_DATA_VALUE))
                // 返回参数格式
                .produces(Sets.newHashSet(MediaType.APPLICATION_JSON_VALUE))
                .tags(Sets.newHashSet("系统：登录"))
                .parameters(
                        Arrays.asList(
                                new ParameterBuilder()
                                        .description("用户名")
                                        .type(new TypeResolver().resolve(String.class))
                                        .name("username")
                                        .defaultValue("admin")
                                        .parameterType("query")
                                        .parameterAccess("access")
                                        .required(true)
                                        .modelRef(new ModelRef("string"))
                                        .build(),
                                new ParameterBuilder()
                                        .description("密码")
                                        .type(new TypeResolver().resolve(String.class))
                                        .name("password")
                                        .defaultValue("123456")
                                        .parameterType("query")
                                        .parameterAccess("access")
                                        .required(true)
                                        .modelRef(new ModelRef("string"))
                                        .build(),
                                new ParameterBuilder()
                                        .description("验证码")
                                        .type(new TypeResolver().resolve(String.class))
                                        .name("captcha")
                                        .parameterType("query")
                                        .parameterAccess("access")
                                        .required(true)
                                        .modelRef(new ModelRef("string"))
                                        .build()
                        )
                )
                .build();
        ApiDescription loginApiDescription = new ApiDescription("auth", "/admin/acl/login", "登录", Arrays.asList(loginOperation), false);
        return Arrays.asList(loginApiDescription);
    }


    /**
     * 是否使用此插件
     *
     * @param documentationType swagger文档类型
     * @return true 启用
     */
    @Override
    public boolean supports(DocumentationType documentationType) {
        return DocumentationType.SWAGGER_2.equals(documentationType);
    }
}
