package com.doudou.wx.api.config;

import com.doudou.wx.api.interceptor.ArgumentResolver;
import com.doudou.wx.api.interceptor.AuthenticateInterceptor;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-11-10
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${local.file.dir}")
    @Getter
    private String localFileServerDir;

    @Value("${local.file.path}")
    @Getter
    private String localFileServerPath;

    /**
     * 本地文件夹要以"flie:" 开头，文件夹要以"/" 结束，example：
     * registry.addResourceHandler("/abc/**").addResourceLocations("file:D:/pdf/");
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/" + this.getLocalFileServerPath() + "/**").addResourceLocations("file:" + this.getLocalFileServerDir() + "/");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticateInterceptor())
            .excludePathPatterns("/api/wechat/login",
                "/api/ad/list",
                "/api/resource/list",
                "/api/resource/detail/**",
                "/api/common/upload")
            .addPathPatterns("/api/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ArgumentResolver());
    }
}
