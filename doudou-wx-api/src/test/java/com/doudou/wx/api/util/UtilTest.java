package com.doudou.wx.api.util;

import com.doudou.core.password.util.AESEncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-11-03
 */
@Slf4j
public class UtilTest {

    @Test
    public void handlePassword() {
        log.info("password : [{}]",AESEncryptUtil.encrypt("liwei1993"));
        log.info("appId : [{}]",AESEncryptUtil.encrypt("wxee1dd006db2c0d64"));
        log.info("appSecret : [{}]",AESEncryptUtil.encrypt("59a4fec61b9788195fd396d3ed74528a"));
    }

}
