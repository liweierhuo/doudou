package com.doudou.wx.api.interceptor;

import com.doudou.core.constant.RedisConstant;
import com.doudou.core.constant.WxApiConstant;
import com.doudou.core.util.RedisUtils;
import com.doudou.core.web.annotation.SessionId;
import java.lang.annotation.Annotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-11-10
 */
@Slf4j
public class ArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SessionId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory) throws Exception {
        // 所有的参数注解
        Annotation[] annotations = parameter.getParameterAnnotations();
        // 逐一处理
        for (Annotation annotation : annotations) {
            if (annotation instanceof SessionId) {
                return RedisUtils.get(RedisConstant.getSessionIdKey(webRequest.getHeader(WxApiConstant.CLIENT_SESSION_ID_KEY)));
            }
        }
        return null;
    }
}
