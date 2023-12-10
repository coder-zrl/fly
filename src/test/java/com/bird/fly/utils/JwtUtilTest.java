package com.bird.fly.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.bird.fly.infra.model.JwtSubjectUserModel;
import com.bird.fly.infra.utils.JwtUtils;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-06
 */
@SpringBootTest
class JwtUtilTest {
    @Test
    void generateToken() {
        String token = JwtUtils.createToken(JwtSubjectUserModel.builder().userId(1L).username("bird").build());
        System.out.println(token);
    }

    @Test
    void validateToken() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ7XCJpZFwiOjEsXCJ1c2VybmFtZVwiOlwiYmlyZFwifSIsImV4cCI6MTcwMTg3NDEyOCwiaWF0IjoxNzAxODU2MTI4fQ.9edxMaNCmWs2okst4zGHEfS72cg8TFpwBs8yEYTUXXI";
        Boolean result = JwtUtils.verifyToken(token);
        System.out.println(result);
    }
}