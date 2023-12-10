package com.bird.fly.infra.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-07
 * Jwt配置属性类
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    //定义token返回头部
    private String header;
    //token前缀
    private String tokenPrefix;
    //签名密钥
    private String secret;
    //有效期
    private long expireTime;
}
