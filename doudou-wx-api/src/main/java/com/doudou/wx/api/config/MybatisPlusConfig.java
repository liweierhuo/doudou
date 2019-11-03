package com.doudou.wx.api.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-10-12
 */
@Configuration
@MapperScan("com.doudou.dao.mapper")
public class MybatisPlusConfig {
    /**
     * 分页插件，自动识别数据库类型
     * @return ~
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public GlobalConfig globalConfiguration() {
        GlobalConfig conf = new GlobalConfig();
        DbConfig dbConfig = new DbConfig();
        dbConfig.setDbType(DbType.MYSQL);
        dbConfig.setLogicDeleteValue("0");
        dbConfig.setLogicNotDeleteValue("1");
        conf.setDbConfig(dbConfig);
        conf.setSqlInjector(new LogicSqlInjector());
        return conf;
    }
}
