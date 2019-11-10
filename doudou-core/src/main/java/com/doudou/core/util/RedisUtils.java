package com.doudou.core.util;

import com.doudou.core.constant.RedisConstant;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author liwei
 * Created by liwei on 2019-03-04.
 */
@Service
@Slf4j
public class RedisUtils {

    private RedisUtils() {
    }


    private static StringRedisTemplate redisTemplate;

    public static String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public static String genericUniqueId(String prefix) {
        String key = String.join(":", RedisConstant.UNIQUE_ID_KEY, prefix);
        //前缀 + 年月日（6位） + 6位唯一序列 + 4位随机数
        StringBuilder sb = new StringBuilder(prefix);
        //年月日
        sb.append(DateFormatUtils.format(new Date(), "yyMMdd"));
        //6位唯一序列
        Long generateId = 0L;
        try {
            generateId = redisTemplate.opsForValue().increment(key, 1L);
            if (generateId > RedisConstant.REDIS_MAX) {
                redisTemplate.opsForValue().set(key, "0");
            }
        } catch (Exception e) {
            log.warn("获取redis失败", e);
            generateId = (long) new Random().nextInt(1000000);
        }
        sb.append(String.format("%06d", generateId));
        //4位随机数
        int random2 = new Random().nextInt(10000);
        sb.append(String.format("%04d", random2));
        return sb.toString();
    }


    /**
     * 加锁可能出现死锁问题
     * 需要手动删除对应的key
     * @param key
     * @param expire
     * @param timeUnit
     * @param callBack
     * @param <T>
     * @return
     */
    public static <T> T lock(String key, int expire, TimeUnit timeUnit, CallBack<T> callBack) {
        log.info("使用redis加锁，key:{},expire :{},timeUnit:{}",key,expire,timeUnit);
        Boolean locked = false;
        try {
            locked = redisTemplate.opsForValue().setIfAbsent(key, "lock_value");
            if (null == locked || !locked) {
                throw new IllegalAccessException("加锁失败，业务处理中");
            }
            Boolean expireSuccess = redisTemplate.expire(key, (long) expire, timeUnit);
            if (null == expireSuccess || !expireSuccess) {
                throw new IllegalAccessException("加锁失败，业务处理中");
            }
            return callBack.invoke();
        } catch (Exception e) {
            log.error("业务执行异常", e);
            throw new RuntimeException(e);
        } finally {
            if (locked !=null && locked) {
                unlock(key);
            }
        }
    }

    public static void put(String key, String value, Long timeout, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(key, timeout, timeUnit);
        } catch (Exception e) {
            log.error("redis 存储失败, key={}, value={}", key, value, e);
        }
    }

    private static void unlock(String key) {
        redisTemplate.delete(key);
    }

    public static <T> T lock(String key, CallBack<T> callBack) {
        return lock(key, 15, TimeUnit.MINUTES, callBack);
    }

    public static <T> T lock(String key, int expire,CallBack<T> callBack) {
        return lock(key, expire, TimeUnit.MINUTES, callBack);
    }

    @Autowired
    private void setRedisTemplate(StringRedisTemplate redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }

    public interface CallBack<T> {
        /**
         * 业务执行回调
         * @return ～
         */
        T invoke();
    }
}
