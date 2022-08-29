package com.itCar.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: BucketAnnotation 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2022/6/28 15:33
 * @DAY_NAME_SHORT: 星期二
 * @Version: v1.0
 */
@Target({ElementType.METHOD,ElementType.TYPE})// METHOD代表是用在方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface BucketAnnotation {
}
