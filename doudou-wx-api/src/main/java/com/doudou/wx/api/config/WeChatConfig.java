package com.doudou.wx.api.config;

import com.doudou.core.properties.WeChatConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Cheney
 * @version $Id: WeChatConfig.class 2020/2/22 12:51 $
 * @description
 */
@Configuration
@EnableConfigurationProperties(WeChatConfigProperties.class)
public class WeChatConfig {
}
