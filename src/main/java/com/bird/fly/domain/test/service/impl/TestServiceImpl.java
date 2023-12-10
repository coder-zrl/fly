package com.bird.fly.domain.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bird.fly.domain.test.cache.TestCacheHelper;
import com.bird.fly.domain.test.service.TestService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-05
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private TestCacheHelper cacheHelper;

    @Override
    public void testMySQL() {

    }

    @Override
    public void testRedis(String key) {
        // 设置键值对
        redisTemplate.opsForValue().set(key, "666");
        // 获取键值对
        String value = redisTemplate.opsForValue().get(key);
        log.info("value:{}", value);
        // key加20
        Long incremented = redisTemplate.opsForValue().increment(key, 20);
        log.info("incremented:{}", incremented);
        // 删除键值对
        redisTemplate.delete("key");
    }

    @Override
    public void testRedisWithNameSpace(Long userId, String username) {
        boolean result = cacheHelper.notInCache(userId, username);
        log.info("result:{}", result);
    }
}
