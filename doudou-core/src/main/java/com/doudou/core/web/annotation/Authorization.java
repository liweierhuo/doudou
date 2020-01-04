package com.doudou.core.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * controller中方法需要登录时，使用的注解
 * @ClassName Authorization
 * @Description
 * @Author shenliuhai
 * @Date 2020/1/4 19:55
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {
}