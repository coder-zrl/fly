package com.bird.fly.infra.redis;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-07
 * Redis集群
 */
public class RedisClusters {
    public static StatefulRedisConnection<String, String> getConnection() {
        RedisURI redisUri = RedisURI.builder()
                .withHost("localhost")
                .withPort(6377)
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();
        return RedisClient.create(redisUri).connect();
    }
}
