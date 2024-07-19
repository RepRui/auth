package com.li.auth.utils;

import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisUtil {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisUtil(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 設置值 登錄使用
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value, TokenUtil.ACCESS_EXPIRE_TIME,TokenUtil.ACCESS_EXPIRE_UNIT);
    }

    /**
     * 取值並更新 檢驗使用
     * @param key
     * @return
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
        //return stringRedisTemplate.opsForValue().getAndExpire(key, TokenUtil.ACCESS_EXPIRE_TIME,TokenUtil.ACCESS_EXPIRE_UNIT);
    }

    /**
     * 更新过期时间
     * @param key
     * @return
     */
    public boolean expire(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.expire(key, TokenUtil.ACCESS_EXPIRE_TIME, TokenUtil.ACCESS_EXPIRE_UNIT));//更新过期时间
    }

    /**
     * 刪除 登出使用
     * @param key
     * @return
     */
    public boolean delete(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.delete(key));
    }


}
