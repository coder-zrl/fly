package com.bird.fly.infra.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bird.fly.infra.annotation.LoginRequired;
import com.bird.fly.infra.utils.JwtUtils;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-07
 * LoginRequired 注解的拦截器
 */
@Configuration
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 检查方法或类上是否有@LoginRequired注解
        LoginRequired methodAnnotation = handlerMethod.getMethodAnnotation(LoginRequired.class);
        LoginRequired classAnnotation = handlerMethod.getBeanType().getAnnotation(LoginRequired.class);
        // 验证是否需要登录
        if (methodAnnotation != null || classAnnotation != null) {
            JwtUtils.verifyToken(request);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
}
