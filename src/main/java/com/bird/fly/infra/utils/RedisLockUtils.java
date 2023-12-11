package com.bird.fly.infra.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.bird.fly.infra.redis.RedisNameSpace;

import io.lettuce.core.SetArgs.Builder;
import io.lettuce.core.api.sync.RedisCommands;
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
            RedisCommands<String, String> client = RedisNameSpace.test.getSyncClient();
            String result = client.set(key, value, Builder.nx().px(expire));
            return StringUtils.equals(result, "OK");
            // 这是原子操作 等价于 SET key value NX EX seconds
            // Boolean locked = stringRedisTemplate.opsForValue().setIfAbsent(key, value, expire, TimeUnit
            //        .SECONDS);
            // return locked != null && locked;
        } catch (Exception e) {
            log.error("tryLock exception, key = {}", key, e);
            return false;
        }
    }

    public static boolean unlock(String keyName) {
        return true;
    }
}
