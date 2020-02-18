package com.doudou.wx.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author liwei
 */
@SpringBootApplication(scanBasePackages = "com.doudou.*")
public class DoudouWxApiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DoudouWxApiApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DoudouWxApiApplication.class);
    }
}
