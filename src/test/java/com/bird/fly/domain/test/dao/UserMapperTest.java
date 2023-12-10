package com.bird.fly.domain.test.dao;

import static com.bird.fly.domain.test.model.table.UserTableDef.USER;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bird.fly.domain.test.model.User;
import com.mybatisflex.core.query.QueryWrapper;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-05
 */
@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void simpleTest() {
        // 增
        int insert = userMapper.insert(User.builder().username("bird").password("fly").build());
        System.out.println(insert);
        // 查
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select()
                .where(USER.USERNAME.eq("bird"));
        User user = userMapper.selectOneByQuery(queryWrapper);
        System.out.println(user);
    }
}