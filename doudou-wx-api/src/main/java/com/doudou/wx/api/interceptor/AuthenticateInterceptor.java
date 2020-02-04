package com.doudou.wx.api.interceptor;

import com.doudou.core.constant.Constant;
import com.doudou.core.redis.RedisManager;
import com.doudou.core.web.annotation.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-11-10
 */
@Slf4j
public class AuthenticateInterceptor extends HandlerInterceptorAdapter {

    //鉴权信息的无用前缀，默认为空
    private String httpHeaderPrefix = "";

    //鉴权失败后返回的错误信息，默认为401 unauthorized
    private String unauthorizedErrorMessage = "401 unauthorized";

    //鉴权失败后返回的HTTP错误码，默认为401
    private int unauthorizedErrorCode = HttpServletResponse.SC_UNAUTHORIZED;

    @Autowired
    private RedisManager redis;

    public void setHttpHeaderPrefix(String httpHeaderPrefix) {
        this.httpHeaderPrefix = httpHeaderPrefix;
    }

    public void setUnauthorizedErrorMessage(String unauthorizedErrorMessage) {
        this.unauthorizedErrorMessage = unauthorizedErrorMessage;
    }

    public void setUnauthorizedErrorCode(int unauthorizedErrorCode) {
        this.unauthorizedErrorCode = unauthorizedErrorCode;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //从header中得到token
        String token = request.getHeader(Constant.HTTP_HEADER_NAME);

        if (token != null && token.length() > 0) {
            //验证token
            String key = redis.getKey(token);
            if (key != null) {
                return true;
            } else {
                log.info("没有找到key。token为:{}", token);
            }
        }

        //如果验证token失败，并且方法注明了Authorization，返回401错误
        //查看方法上是否有注解
        //查看方法所在的Controller是否有注解
        if (method.getAnnotation(Authorization.class) != null
                || handlerMethod.getBeanType().getAnnotation(Authorization.class) != null) {
            log.info("401了。token为:{}", token);
            response.setStatus(unauthorizedErrorCode);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
            writer.write(unauthorizedErrorMessage);
            writer.close();
            return false;
        }
        return true;

    }
}
