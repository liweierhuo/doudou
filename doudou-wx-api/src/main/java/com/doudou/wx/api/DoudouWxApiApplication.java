package com.doudou.wx.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author liwei
 */
@SpringBootApplication(scanBasePackages = "com.doudou")
@EnableTransactionManagement
public class DoudouWxApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoudouWxApiApplication.class, args);
    }
}
