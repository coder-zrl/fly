package com.bird.fly.infra.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.bird.fly.infra.utils.TraceIdUtils;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-11
 */
@Aspect
@Component
public class TraceableAspect {
    @Around("@within(com.bird.fly.infra.annotation.Traceable)")
    public Object handleAround(ProceedingJoinPoint pjp) throws Throwable {
        TraceIdUtils.injectTraceId();
        try {
            return pjp.proceed();
        } finally {
            TraceIdUtils.removeTraceId();
        }
    }
}