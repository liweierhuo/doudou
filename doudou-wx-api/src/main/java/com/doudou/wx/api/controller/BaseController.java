package com.doudou.wx.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * @author liwei
 */
public abstract class BaseController {
    /**
     * Servlet APIs。
     */
    private static ThreadLocal<HttpServletRequest> REQUEST_LOCAL = new ThreadLocal<>();
    private static ThreadLocal<HttpServletResponse> RESPONSE_LOCAL = new ThreadLocal<>();

    /**
     * 获取 请求对象。
     *
     * @author : hebad90@163.com
     */
    protected HttpServletRequest getRequest() {
        return REQUEST_LOCAL.get();
    }

    /**
     * 获取响应对象。
     *
     * @author : hebad90@163.com
     */
    protected HttpServletResponse getResponse() {
        return RESPONSE_LOCAL.get();
    }

    protected HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 设置Servlet APIs
     * @author : hebad90@163.com
     */
    @ModelAttribute
    private void setServletAPIs(HttpServletRequest request, HttpServletResponse response) {
        REQUEST_LOCAL.set(request);
        RESPONSE_LOCAL.set(response);
    }

    protected String getReferer() {
        return getRequest().getHeader("referer");
    }

    // --------------------------------------------

    protected String getUserAgent(){
        return getRequest().getHeader("User-Agent");
    }
    

    public String baseUri() {
        String url = getRequest().getRequestURL().toString();
        String uri = getRequest().getRequestURI();
        return StringUtils.removeEnd(url, uri);
    }

    protected String redirect(String targetUrl) {
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + targetUrl;
    }



}
