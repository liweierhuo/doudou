package com.doudou.wx.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liwei
 */
@SpringBootApplication(scanBasePackages = "com.doudou")
@MapperScan("com.doudou.dao.mapper.*")
public class DoudouWxApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoudouWxApiApplication.class, args);
    }
}
