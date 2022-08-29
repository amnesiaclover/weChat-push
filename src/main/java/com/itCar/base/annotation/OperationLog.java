package com.itCar.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: OperationLog 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/28 16:21
 * @Version: v1.0
 */
@Target(ElementType.METHOD)//注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME)//注解在哪个阶段执行
public @interface OperationLog {

    // 操作的方法名
    String value() default "";

    // 模块名称
    String moduleName() default "";
}
