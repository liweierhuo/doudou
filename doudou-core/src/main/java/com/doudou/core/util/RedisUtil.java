package com.doudou.core.util;

import com.alibaba.fastjson.JSONObject;
import com.doudou.core.constant.RedisConstant;
import com.doudou.core.exception.RedisLockException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * redis 工具类
 *
 * @Author: liwei
 * @Date: 2019/9/18  17:53
 * @Version 1.0
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class RedisUtil {

    private RedisUtil() {
    }

    @Autowired
    @Qualifier("redisLockTemplate")
    private RedisTemplate<String,Object> redisLockTemplate;

    private static RedisTemplate<String,Object> redisTemplate;

    //解决static方法 调用注入对象的方法
    @Autowired
    public void setUserService(RedisTemplate<String,Object> redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public static boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("is error",e);
            return false;
        }
    }

    public static Boolean exists(byte[] key) {
        Object execute = redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.exists(key));
        return (Boolean) execute;
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public static boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("is error",e);
            return false;
        }
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public static boolean expireAt(String key, Date expiredTime) {
        try {
            if (expiredTime != null) {
                return redisTemplate.expireAt(key, expiredTime);
            }
            return true;
        } catch (Exception e) {
            log.error("is error",e);
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public static long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    public static void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    public void del(byte[] key) {
        redisTemplate.execute((RedisCallback) connection -> {
            connection.del(key);
            return null;
        });
    }

    // ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public static String get(String key) {
        return key == null ? null : (String) redisTemplate.opsForValue().get(key);
    }

    public static byte[] get(byte[] key) {
        Object execute = redisTemplate.execute((RedisCallback<byte[]>) connection -> connection.get(key));
        return (byte[]) execute;
    }

    /**
     * 普通缓存放入,如果key已经存在 则覆盖
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public static boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, JSONObject.toJSON(value).toString());
            return true;
        } catch (Exception e) {
            log.error("is error",e);
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public static boolean setex(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, JSONObject.toJSON(value).toString(), time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("setex is error ",e);
            return false;
        }
    }

    public static void setex(byte[] key, byte[] value, int seconds) {
        redisTemplate.execute((RedisCallback) connection -> {
            connection.setEx(key, seconds, value);
            return null;
        });
    }

    /**
     * 递增
     *
     * @param key 键
     * @param by  要增加几(大于0)
     * @return
     */
    public static long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key 键
     * @param by  要减少几(小于0)
     * @return
     */
    public static long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    // ================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public static Object hget(String key, String fieldKey) {
        return redisTemplate.opsForHash().get(key, fieldKey);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public static boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("is error",e);
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public static boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("is error",e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public static boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error("is error",e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public static boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("is error",e);
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public static void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public static boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public static double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public static double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    // ============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public static Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("is error",e);
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public static boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("is error",e);
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("is error",e);
            return 0;
        }
    }

    /**
     * 将set数据放入缓存(设置key的过期时间)
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            log.error("is error",e);
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public static long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("is error",e);
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public static long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            log.error("is error",e);
            return 0;
        }
    }

    // ===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束
     *              0 到 -1代表所有值
     * @return
     */
    public static List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("is error",e);
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public static long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("is error",e);
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引
     *              index>=0时， 0 表头， 1 第二个元素，依次类推；
     *              index<0时， -1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public static Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("is error",e);
            return null;
        }
    }

    /**
     * 将value放入list缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("is error",e);
            return false;
        }
    }

    /**
     * 将list放入缓存（设置过期时间)
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("is error",e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("is error",e);
            return false;
        }
    }

    /**
     * 将list放入缓存（设置过期时间）
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("is error",e);
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public static boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("is error",e);
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public static long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            log.error("is error",e);
            return 0;
        }
    }

    public <T> T buessineslock(String key,String requestId, int expire,CallBack<T> callBack) {
        String lockKey = getLockKey(key);
        log.info("使用redis加锁，key:[{}],expire :[{}],requestId:[{}]",key,expire,requestId);
        boolean locked = false;
        try {
            locked = (Boolean) redisLockTemplate.execute((RedisCallback<Boolean>) connection -> {
                RedisSerializer valueSerializer = redisLockTemplate.getValueSerializer();
                RedisSerializer keySerializer = redisLockTemplate.getKeySerializer();
                Object obj = connection.execute("set", keySerializer.serialize(lockKey),
                    valueSerializer.serialize(requestId),
                    "NX".getBytes(StandardCharsets.UTF_8),
                    "EX".getBytes(StandardCharsets.UTF_8),
                    String.valueOf(expire).getBytes(StandardCharsets.UTF_8));
                return obj != null;
            });
            if (!locked) {
                log.error("[{}]-[{}]加锁失败，业务处理中",key,requestId);
                throw new RedisLockException("加锁失败，业务处理中");
            }
            return callBack.invoke();
        } catch (RedisLockException e) {
            log.error("加锁失败，业务处理中", e);
            throw new RedisLockException("业务处理中，请勿重复操作");
        } catch (Exception e) {
            log.error("业务执行异常", e);
            throw e;
        } finally {
            if (locked) {
                unlock(lockKey,requestId);
            }
        }
    }

    private void unlock(String key,String requestId) {
        Object lockValue = redisLockTemplate.opsForValue().get(key);
        if (lockValue != null && lockValue.equals(requestId)) {
            redisLockTemplate.delete(key);
        }
    }

    /**
     * 获取Lock key
     * @param key 业务key
     * @return key
     */
    private String getLockKey(@NotNull String key) {
        return String.join(":", RedisConstant.REDIS_BASE_KEY,"lock", key);
    }

    /**
     *
     * @param prefix
     * @return
     */
    public String genericUniqueId(String prefix) {
        //前缀 + 年月日（6位） + 6位唯一序列 + 4位随机数
        StringBuilder sb = new StringBuilder(prefix);
        //年月日
        String today = DateFormatUtils.format(new Date(), "yyMMdd");
        String key = String.join(":", RedisConstant.REDIS_BASE_KEY,"genericId",prefix+today);
        sb.append(today);
        //6位唯一序列
        long generateId = 0L;
        try {
            generateId = incr(key,1);
            expireAt(key, DateTime.now().plusDays(1).toDate());
            if (generateId > 99998) {
                throw new IllegalArgumentException(String.format("每日最多可创建%d",99998));
            }
            sb.append(String.format("%06d", generateId));
        } catch (Exception e) {
            log.warn("获取redis失败", e);
            int random = new Random().nextInt(1000000);
            sb.append(String.format("%06d", random));
        }
        //4位随机数
        int random = new Random().nextInt(10000);
        sb.append(String.format("%04d", random));
        return sb.toString();
    }

    public interface CallBack<T> {
        /**
         * 业务执行回调
         * @return ～
         */
        T invoke();
    }

}