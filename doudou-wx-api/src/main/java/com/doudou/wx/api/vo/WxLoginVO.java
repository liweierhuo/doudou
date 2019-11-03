package com.doudou.wx.api.vo;

import lombok.Data;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-11-02
 */
@Data
public class WxLoginVO {

    private String code;

    private String signature;

    private String iv;

    private String encryptedData;

    private String rawData;
}
