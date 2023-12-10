package com.bird.fly.infra.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-11
 * 用于非 Http 接口的线程注入TraceId
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Traceable {
}
