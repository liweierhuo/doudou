package com.doudou.admin.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-02-23
 */
@Component
@ConfigurationProperties(prefix = "wechat.wxa")
@Data
public class WeChatConfigProperties {

    /**
     * APP ID
     */
    private String appId;

    /**
     * APP Secret
     */
    private String appSecret;

    /**
     * Union Login URL
     */
    private String loginUrl;

    /**
     * AccessToken URL
     */
    private String accessTokenUrl;

    /**
     * 下发小程序和公众号统一的服务消息
     */
    private String uniformSendUrl;

    /**
     * 小程序消息
     */
    private String miniTemplateUrl;

}
