package com.bird.fly.infra.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-07
 * Redis分布式锁
 */
@Slf4j
@Component
public class RedisLockUtils {
    private static StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisLockUtils(StringRedisTemplate stringRedisTemplate) {
        RedisLockUtils.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 加分布式锁
     *
     * @param expire 过期时间，单位是毫秒
     * @return 加结果
     */
    public static boolean tryLock(String key, String value, long expire) {
        try {
            Boolean locked = stringRedisTemplate.opsForValue().setIfAbsent(key, value, expire, TimeUnit.MILLISECONDS);
            return locked != null && locked;
        } catch (Exception e) {
            log.error("tryLock exception, key = {}", key, e);
            return false;
        }
    }

    public static boolean unlock(String keyName) {
        return true;
    }
}
