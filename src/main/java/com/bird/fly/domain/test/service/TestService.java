package com.bird.fly.domain.test.service;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-05
 */
public interface TestService {
    void testMySQL();
    void testRedis(String key);
    void testRedisWithNameSpace(Long userId, String username);
}
