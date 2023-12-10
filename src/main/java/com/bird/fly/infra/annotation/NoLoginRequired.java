package com.bird.fly.infra.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-07
 * 标记某方法不需要鉴权 暂不开发
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Deprecated // 暂不开发
public @interface NoLoginRequired {
}
