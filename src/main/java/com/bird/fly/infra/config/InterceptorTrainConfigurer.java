package com.bird.fly.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bird.fly.infra.interceptor.LoginInterceptor;


/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-07
 * 添加接口拦截器，LoginInterceptor是校验是否需要对方法鉴权
 */
@Configuration
public class InterceptorTrainConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
    }
}
