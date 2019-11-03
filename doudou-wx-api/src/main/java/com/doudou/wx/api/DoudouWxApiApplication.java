package com.doudou.wx.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.doudou")
public class DoudouWxApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoudouWxApiApplication.class, args);
    }

}
