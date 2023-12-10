package com.bird.fly.test;

import org.junit.Test;

import com.bird.fly.infra.redis.RedisNameSpace;

import io.lettuce.core.api.sync.RedisCommands;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-07
 */
public class CommonTest {
    @Test
    public void testRedisCase(){
        RedisCommands<String, String> client = RedisNameSpace.test.getSyncClient();
        String redisKey = RedisNameSpace.test.toRedisKey("user", "sign");
        System.out.println(redisKey);
        client.set(redisKey, "777777");
    }
}
