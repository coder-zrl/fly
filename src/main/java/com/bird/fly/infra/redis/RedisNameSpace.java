package com.bird.fly.infra.redis;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-07
 * Redis命名空间枚举
 */
public enum RedisNameSpace {
    test(RedisClusters.getConnection(), "test");

    private final StatefulRedisConnection<String, String> connection;
    private final String keyPrefix;

    public RedisAsyncCommands<String, String> getAsyncClient() {
        return connection.async();
    }

    public RedisCommands<String, String> getSyncClient() {
        return connection.sync();
    }

    RedisNameSpace(StatefulRedisConnection<String, String> connection, String keyPrefix) {
        this.connection = connection;
        this.keyPrefix = keyPrefix;
    }

    public String toRedisKey(Object... values) {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(keyPrefix);
        for (Object value : values) {
            stringBuffer.append("_");
            stringBuffer.append(value);
        }
        return stringBuffer.toString();
    }
}
