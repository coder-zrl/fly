package com.bird.fly.infra.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-07
 * 方法分布式锁注解，参数可以配合@LockKey一起使用
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {
    /**
     * 分布式锁前缀
     */
    String prefix() default "redis_lock";

    /**
     * 分布式锁key，如果为空且未使用@LockKey，则默认使用方法名作key
     */
    String key() default "";

    /**
     * 分布式锁过期时间
     */
    long expire() default 100L;
}
