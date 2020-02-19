package com.doudou.wx.api.vo;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-11-02
 */
@Data
@NoArgsConstructor
public class WxLoginVO implements Serializable {

    private String code;

    private String signature;

    private String iv;

    private String encryptedData;

    private String rawData;
}
