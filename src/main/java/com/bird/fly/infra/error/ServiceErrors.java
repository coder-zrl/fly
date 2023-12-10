package com.bird.fly.infra.error;

import javax.validation.constraints.NotNull;

import com.bird.fly.infra.exception.ApiServiceException;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-06
 */
public enum ServiceErrors {
    success(1, "success"),
    user_not_login(2, "用户未登录"),
    try_redis_lock_failed(3, "分布式锁加锁失败"),
    un_know(404, "未知错误"),
    ;

    private final int errorCode;
    private final String errorMessage;

    ServiceErrors(int errorCode, @NotNull String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ApiServiceException createException() {
        return ApiServiceException.ofMessage(errorCode, getErrorMessage());
    }
}
