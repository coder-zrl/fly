package com.bird.fly.infra.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-07
 * Jwt中使用的User类
 */
@Getter
@Setter
@Builder
public class JwtSubjectUserModel {
    private long userId;
    private String username;
}
