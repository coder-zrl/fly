package com.bird.fly.infra.aspect;

import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-07
 * 方法执行耗时监控
 */
@Aspect
@Component
@Slf4j
public class FunctionTimeWatcherAspect {
    @Around("@annotation(com.bird.fly.infra.annotation.TimeWatcher)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = StopWatch.createStarted();
        Object proceed = joinPoint.proceed();
        stopWatch.stop();
        log.info("{} executed in {} ms", joinPoint.getSignature().getName(), stopWatch.getTime());
        return proceed;
    }
}
