package com.doudou.wx.api.config;

import com.doudou.wx.api.interceptor.ArgumentResolver;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
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
     * 本地文件夹要以"flie:" 开头，文件夹要以"/" 结束，example： registry.addResourceHandler("/abc/**").addResourceLocations("file:D:/pdf/");
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/" + this.getLocalFileServerPath() + "/**")
            .addResourceLocations("file:" + this.getLocalFileServerDir() + "/");
        registry.addResourceHandler("/" + this.getLocalFileServerPath() + "/ad/**")
            .addResourceLocations("file:" + this.getClass().getResource("/file/ad/").getPath());
        registry.addResourceHandler("/" + this.getLocalFileServerPath() + "/tool/**")
            .addResourceLocations("file:" + this.getClass().getResource("/file/tool/").getPath());


    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ArgumentResolver());
    }
}
