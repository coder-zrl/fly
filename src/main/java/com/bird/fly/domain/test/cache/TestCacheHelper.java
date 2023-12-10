package com.bird.fly.domain.test.cache;

import org.springframework.stereotype.Repository;

import com.bird.fly.infra.redis.RedisNameSpace;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-11
 */
@Repository
public class TestCacheHelper {
    public boolean notInCache(Long userId, String username) {
        return RedisNameSpace.test.getSyncClient().exists(genUserTestKey(userId, username)) == 1;
    }

    private String genUserTestKey(Long userId, String username) {
        return RedisNameSpace.test.toRedisKey(userId, username);
    }
}
