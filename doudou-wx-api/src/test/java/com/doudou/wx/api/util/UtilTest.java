package com.doudou.wx.api.util;

import com.alibaba.druid.filter.config.ConfigTools;
import com.doudou.core.constant.RedisConstant;
import com.doudou.core.password.util.AESEncryptUtil;
import com.doudou.core.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-11-03
 */
@Slf4j
//@SpringBootTest
//@RunWith(SpringRunner.class)
public class UtilTest {

    @Test
    public void handlePassword() throws Exception {
        log.info("password : [{}]",AESEncryptUtil.encrypt("liwei1993"));
        log.info("appId : [{}]",AESEncryptUtil.encrypt("wxee1dd006db2c0d64"));
        log.info("appSecret : [{}]",AESEncryptUtil.encrypt("59a4fec61b9788195fd396d3ed74528a"));
        log.info("root:{}", ConfigTools.encrypt("root"));
        log.info("localhostPass:{}",ConfigTools.encrypt("liwei1993"));
    }

    @Test
    public void handleUserNameAndPassword() {
        String join = String.join(";", "slh", "liwei");

        String key = String.join(":", RedisConstant.UNIQUE_ID_KEY, "w");

        String w = RedisUtils.genericUniqueId("w");

        String slh = RedisConstant.getAccessTokenRedisKey("slh");


        System.out.println(join);
        System.out.println(key);
        System.out.println(w);
        System.out.println(slh);
    }

}
