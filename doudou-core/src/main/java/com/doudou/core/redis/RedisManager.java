package com.doudou.core.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName RedisManager
 * @Description
 * @Author shenliuhai
 * @Date 2020/1/4 16:08
 **/
@Service
public class RedisManager extends AbstractRedisTokenManager{

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 默认过期时长，单位：秒
     */
    public static final long DEFAULT_EXPIRE = 60 * 60 * 2;

    /**
     * 不设置过期时长
     */
    public static final long NOT_EXPIRE = -1;


    /**
     * Redis中Key的前缀
     */
    private static final String REDIS_KEY_PREFIX = "AUTHORIZATION_KEY_";

    /**
     * Redis中Token的前缀
     */
    private static final String REDIS_TOKEN_PREFIX = "AUTHORIZATION_TOKEN_";


    @Autowired
    private StringJedis jedis;



    @Override
    protected void delSingleRelationshipByKey(String key) {
        String token = getToken(key);
        if (token != null) {
            delete(formatKey(key), formatToken(token));
        }
    }

    @Override
    public void delRelationshipByToken(String token) {
        if (singleTokenWithUser) {
            String key = getKey(token);
            delete(formatKey(key), formatToken(token));
        } else {
            delete(formatToken(token));
        }
    }

    @Override
    protected void createSingleRelationship(String key, String token) {
        String oldToken = get(formatKey(key));
        if (oldToken != null) {
            delete(formatToken(oldToken), formatKey(key));
        }
        if ( null == tokenExpireSeconds){

            set(formatToken(token), key);
        }
        else {
            set(formatKey(key), token, tokenExpireSeconds);
            set(formatToken(token), key, tokenExpireSeconds);
        }

    }

    @Override
    protected void createMultipleRelationship(String key, String token) {
        if ( null == tokenExpireSeconds ){
            set(formatToken(token), key);
        }
        else {
            set(formatToken(token), key, tokenExpireSeconds);
        }
    }

    @Override
    public String getKeyByToken(String token) {
        return get(formatToken(token));
    }

    @Override
    protected void flushExpireAfterOperation(String key, String token) {
        if (singleTokenWithUser) {
            expire(formatKey(key), tokenExpireSeconds);
        }
        expire(formatToken(token), tokenExpireSeconds);
    }

    private String get(String key) {
        return jedis.getValue(key);
    }

    private void set(String key, String value, long expireSeconds) {
        jedis.setValue(key, value, expireSeconds);
    }

    private void set(String key, String value) {
        jedis.setValue(key, value);
    }

    private void expire(String key, long seconds) {
        jedis.expire(key, seconds);
    }

    private void delete(String... keys) {
        for (String key : keys) {
            jedis.delete(key);
        }
    }

    private String formatKey(String key) {
        return REDIS_KEY_PREFIX + key;
    }

    private String formatToken(String token) {
        return REDIS_TOKEN_PREFIX + token;
    }


    public String getToken(String key) {
        return get(formatKey(key));
    }



    /**
     * 检查当前key存不存在
     * @author shenliuhai
     * @date 2020/1/4 16:56
     * @param key
     * @return boolean
     */
    public boolean existsKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 重名名key，如果newKey已经存在，则newKey的原值被覆盖
     *
     * @param oldKey
     * @param newKey
     */
    public void renameKey(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * newKey不存在时才重命名
     *
     * @param oldKey
     * @param newKey
     * @return 修改成功返回true
     */
    public boolean renameKeyNotExist(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 删除key
     *
     * @param key
     */
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除多个key
     *
     * @param keys
     */
    public void deleteKey(String... keys) {
        Set<String> kSet = Stream.of(keys).map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    /**
     * 删除Key的集合
     *
     * @param keys
     */
    public void deleteKey(Collection<String> keys) {
        Set<String> kSet = keys.stream().map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    /**
     * 设置key的生命周期
     *
     * @param key
     * @param time
     * @param timeUnit
     */
    public void expireKey(String key, long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 指定key在指定的日期过期
     *
     * @param key
     * @param date
     */
    public void expireKeyAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    /**
     * 查询key的生命周期
     *
     * @param key
     * @param timeUnit
     * @return
     */
    public long getKeyExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 将key设置为永久有效
     *
     * @param key
     */
    public void persistKey(String key) {
        redisTemplate.persist(key);
    }
}
