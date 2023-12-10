package com.bird.fly.infra.aspect;

import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import lombok.extern.slf4j.Slf4j;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-07
 */
// 全局监控
@Aspect
//@Component
@Slf4j
@Deprecated
public class GlobalMonitorAspect {
    // 全局方法耗时监控
    @Around("execution(* com.bird..*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = StopWatch.createStarted();
        Object proceed = joinPoint.proceed();
        stopWatch.stop();
        log.info("{} executed in {} ms", joinPoint.getSignature().getName(), stopWatch.getTime());
        return proceed;
    }

    // 全局异常监控
    @AfterThrowing(pointcut = "execution(* com.bird..*.*(..))", throwing = "e")
    public void handleThrowing(JoinPoint joinPoint, Throwable e) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Method " + methodName + " has an exception: " + e.getClass());
    }
}
