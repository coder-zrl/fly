package com.bird.fly.infra.config;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.bird.fly.infra.model.ResponseView;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-06
 */
@ControllerAdvice
public class ResponseViewAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 这个方法决定哪些请求应该被包装。在这个例子中，所有请求都将被包装。
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (!request.getURI().getPath().startsWith("/api")){
            return body;
        }
        if (body instanceof ResponseView) {
            return body;
        }
        return ResponseView.success(body);
    }
}
