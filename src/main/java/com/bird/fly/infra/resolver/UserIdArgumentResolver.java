package com.bird.fly.infra.resolver;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bird.fly.infra.annotation.UserId;
import com.bird.fly.infra.utils.JwtUtils;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-06
 * 注入UserId
 */
@Component
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {
    private static final ThreadLocal<Long> userIdThreadLocal = new ThreadLocal<>();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Long.class) && parameter.hasParameterAnnotation(
                UserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        return JwtUtils.parseUserIdFromToken(request);
    }

    public static void setUserId(Long userId) {
        userIdThreadLocal.set(userId);
    }
}
