package com.doudou.wx.api.interceptor;

import com.doudou.core.constant.WxApiConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-11-10
 */
@Slf4j
public class AuthenticateInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(WxApiConstant.CLIENT_SESSION_ID_KEY);
//        if (StringUtils.isEmpty(token)) {
//            log.error("token is null,please to login");
//            throw new NoLoginException(WxApiConstant.NO_LOGIN_MESSAGE);
//        }
//        // 未登录, 抛出异常
//        String openId = RedisUtils.get(RedisConstant.getSessionIdKey(token));
//        if (StringUtils.isEmpty(openId)) {
//            log.error("token is expired ,please to login");
//            throw new NoLoginException(WxApiConstant.NO_LOGIN_MESSAGE);
//        }
        return super.preHandle(request, response, handler);
    }
}
