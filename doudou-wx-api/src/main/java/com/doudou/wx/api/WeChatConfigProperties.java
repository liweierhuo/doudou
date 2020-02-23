package com.doudou.wx.api;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Cheney
 * @version $Id: WeChatConfigProperties.class 2020/2/22 12:41 $
 * @description
 */
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
     *
     */
    private String paidUnionIdUrl;


}
