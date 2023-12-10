package com.bird.fly.infra.exception;

import lombok.Getter;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-06
 */
@Getter
public class ApiServiceException extends RuntimeException {
    int errorCode;
    String errorMessage;

    public ApiServiceException(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static ApiServiceException ofMessage(int errorCode, String errorMessage) {
        return new ApiServiceException(errorCode, errorMessage);
    }
}
