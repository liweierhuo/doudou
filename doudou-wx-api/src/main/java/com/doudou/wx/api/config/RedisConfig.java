package com.doudou.wx.api.config;

import com.doudou.core.redis.RedisManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RedisConfig
 * @Description
 * @Author shenliuhai
 * @Date 2020/1/4 15:51
 **/
@Configuration
public class RedisConfig {

    @Bean
    public RedisManager redisManager() {
        return new RedisManager();
    }

}
