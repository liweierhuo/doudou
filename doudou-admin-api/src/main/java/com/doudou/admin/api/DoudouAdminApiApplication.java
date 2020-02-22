package com.doudou.admin.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 启动类
 * @author liwei
 */
@SpringBootApplication(scanBasePackages = "com.doudou.*")
public class DoudouAdminApiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DoudouAdminApiApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DoudouAdminApiApplication.class);
    }

}
