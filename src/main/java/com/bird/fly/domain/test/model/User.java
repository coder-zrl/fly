package com.bird.fly.domain.test.model;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

import lombok.Builder;
import lombok.Data;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-05
 */
@Data
@Table("user")
@Builder
public class User {
    @Id(keyType = KeyType.Auto)
    private Long id;
    private String username;
    private String password;
}
