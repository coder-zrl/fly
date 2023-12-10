package com.bird.fly.infra.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bird.fly.infra.filter.TraceFilter;
import com.bird.fly.infra.resolver.UserIdArgumentResolver;

import lombok.extern.slf4j.Slf4j;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-06
 */
@Slf4j
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Autowired
    private UserIdArgumentResolver userIdArgumentResolver;

    @Bean
    @ConditionalOnMissingBean({TraceFilter.class})
    @Order(Ordered.HIGHEST_PRECEDENCE + 101)
    public FilterRegistrationBean<TraceFilter> traceFilterFilterRegistrationBean() {
        FilterRegistrationBean<TraceFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TraceFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("traceFilter");
        return registrationBean;
    }

    // todo 注册JwtFilter

    //    @Bean
    //    @Order(Ordered.HIGHEST_PRECEDENCE + 102) //比traceIdFilter晚执行
    //    public FilterRegistrationBean<JwtFilter> jwtFilter() {
    //        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
    //        registrationBean.setFilter(new JwtFilter());
    //        registrationBean.addUrlPatterns("/api/*");
    //        return registrationBean;
    //    }`

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userIdArgumentResolver);
    }
}
