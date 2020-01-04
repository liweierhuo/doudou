package com.doudou.core.redis;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName StringJedis
 * @Description
 * @Author shenliuhai
 * @Date 2020/1/4 16:22
 **/
@Service
public class StringJedis extends BaseRedisDao<String> {

    @Override
    public void setValue(String key, String s) {
        super.setValue(key, s);
    }

    @Override
    public void setValue(String key, String s, long timeout) {
        super.setValue(key, s, timeout);
    }

    @Override
    public String getValue(String key) {
        return super.getValue(key);
    }

    @Override
    public long listPush(String key, String s) {
        return super.listPush(key, s);
    }

    @Override
    public long listPushAll(String key, List<String> t) {
        return super.listPushAll(key, t);
    }

    @Override
    public long listRemove(String key, long count, String s) {
        return super.listRemove(key, count, s);
    }

    @Override
    public String listIndex(String key, long index) {
        return super.listIndex(key, index);
    }

    @Override
    public List<String> listRang(String key, long star, long stop) {
        return super.listRang(key, star, stop);
    }

    @Override
    public List<String> listAll(String key) {
        return super.listAll(key);
    }

    @Override
    public void delete(String key) {
        super.delete(key);
    }

    @Override
    public void expire(String key, long seconds){
        super.expire(key, seconds);
    }

    @Override
    public List<String> getKeysByPrefix(String prefix) {
        return super.getKeysByPrefix(prefix);
    }

}
