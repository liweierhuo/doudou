package com.doudou.core.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liwei
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SessionId {
    /**
     * 是否为必选参数; 默认为 true
     * <p>
     *     当设置为 true 时,若没有获取到 sessionId , 将会抛出 {@link com.doudou.core.exception.NoLoginException}
     * </p>
     * @return ~
     */
    boolean required() default true;
}
