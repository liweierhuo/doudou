package com.doudou.wx.api.config;

import com.doudou.core.password.DruidEncryptFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-10-12
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    private static final String FILTER_ENCRYPT_PREFIX = "spring.datasource.druid.filter.encrypt";

    @Bean
    @ConfigurationProperties(FILTER_ENCRYPT_PREFIX)
    @ConditionalOnProperty(prefix = FILTER_ENCRYPT_PREFIX, name = "enabled")
    @ConditionalOnMissingBean
    public DruidEncryptFilter druidEncryptFilter() {
        return new DruidEncryptFilter();
    }
}
