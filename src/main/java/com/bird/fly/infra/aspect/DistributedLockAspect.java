package com.bird.fly.infra.aspect;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-07
 */

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.bird.fly.infra.annotation.DistributedLock;
import com.bird.fly.infra.annotation.LockKey;
import com.bird.fly.infra.error.ServiceErrors;
import com.bird.fly.infra.utils.RedisLockUtils;

import cn.hutool.core.lang.UUID;

@Aspect
@Component
public class DistributedLockAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Around("@annotation(distributedLock)")
    public Object lock(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
        String key = buildRedisLockKey(joinPoint, distributedLock);
        String value = UUID.fastUUID().toString();
        // 尝试获取锁
        boolean locked = RedisLockUtils.tryLock(key, value, 1000);
        if (locked) {
            try {
                // 获取锁成功，执行方法
                return joinPoint.proceed();
            } finally {
                // 释放锁
                redisTemplate.delete(key);
            }
        } else {
            // 获取锁失败，抛出异常或者返回错误信息
            throw ServiceErrors.try_redis_lock_failed.createException();
        }
    }

    private String buildRedisLockKey(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) {
        // Part1. 有key则直接返回
        if (!StringUtils.isEmpty(distributedLock.key())) {
            return distributedLock.prefix() + "_" + distributedLock.key();
        }
        // Part2. 如果有被@LockKey注解标记的参数，则使用顺序标记的参数值作为key
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();
        Parameter[] parameters = method.getParameters();
        StringBuilder keyBuilder = new StringBuilder();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getAnnotation(LockKey.class) != null) {
                if (keyBuilder.length() > 0) {
                    keyBuilder.append("_");
                }
                keyBuilder.append(args[i]);
            }
        }
        String key = keyBuilder.toString();
        if (StringUtils.isNotEmpty(key)) {
            return distributedLock.prefix() + "_" + key;
        }
        // Part3. 没有配置使用方法名兜底
        return distributedLock.prefix() + "_" + signature.getName();
    }
}
