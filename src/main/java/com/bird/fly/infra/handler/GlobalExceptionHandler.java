package com.bird.fly.infra.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bird.fly.infra.exception.ApiServiceException;
import com.bird.fly.infra.model.ResponseView;


/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-06
 * ApiServiceException 异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiServiceException.class)
    public ResponseEntity<ResponseView<Void>> handleException(ApiServiceException e) {
        return new ResponseEntity<>(new ResponseView<>(e.getErrorCode(), e.getErrorMessage(), null),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
