package com.doudou.core.constant;

import javax.validation.constraints.NotNull;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-11-10
 */
public final class RedisConstant {

    public static final String REDIS_BASE_KEY = "global:doudou";

    private static final String ACCESS_TOKEN_KEY = "access-token";

    private static final String SESSION_ID_KEY = "session-id";

    public static String getMiniAccessTokenRedisKey() {
        return String.join(":",REDIS_BASE_KEY,"mini-program",ACCESS_TOKEN_KEY);
    }

    public static String getSessionIdKey(String token) {
        return String.join(":",REDIS_BASE_KEY,SESSION_ID_KEY,token);
    }
}
