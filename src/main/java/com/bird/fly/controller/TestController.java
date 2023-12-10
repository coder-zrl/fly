package com.bird.fly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bird.fly.domain.test.service.TestService;
import com.bird.fly.infra.annotation.DistributedLock;
import com.bird.fly.infra.annotation.LockKey;
import com.bird.fly.infra.annotation.LoginRequired;
import com.bird.fly.infra.annotation.TimeWatcher;
import com.bird.fly.infra.annotation.UserId;
import com.bird.fly.view.TestView;

import lombok.extern.slf4j.Slf4j;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-05
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/testGet")
    @TimeWatcher
    @LoginRequired
    public TestView testGet() {
        //        throw ServiceErrors.un_know.createException();
        return TestView.builder().username("bird").build();
    }

    @GetMapping("/testTraceId")
    public void testTraceId() {
        log.debug("testTraceId");
        log.info("testTraceId");
        log.warn("testTraceId");
        log.error("testTraceId");
    }

    @GetMapping("/testUserId")
    public void testUserId(@UserId Long userId) {
        log.info("userId:{}", userId);
    }

    @GetMapping("/testRedisLock")
    @DistributedLock
    public void testRedisLock(@LockKey Long userId, String username, @LockKey String password) {
        log.info("testRedisLock userId:{}, username:{}, password:{}", userId, username, password);
    }
}
