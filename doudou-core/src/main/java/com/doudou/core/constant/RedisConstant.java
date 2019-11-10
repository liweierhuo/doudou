package com.doudou.core.constant;

import javax.validation.constraints.NotNull;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-11-10
 */
public final class RedisConstant {

    private static final String REDIS_BASE_KEY = "pingping:miniprogram";

    private static final String ACCESS_TOKEN_KEY = "access-token";

    public static final String UNIQUE_ID_KEY = "unique-id";

    public static final int REDIS_MAX = 99999;

    public static final String SESSION_ID_KEY = "session-id";

    public static String getAccessTokenRedisKey(@NotNull String openId) {
        return String.join(":",REDIS_BASE_KEY,ACCESS_TOKEN_KEY,openId);
    }

    public static String getSessionIdKey(String token) {
        return String.join(":",REDIS_BASE_KEY,SESSION_ID_KEY,token);
    }
}
