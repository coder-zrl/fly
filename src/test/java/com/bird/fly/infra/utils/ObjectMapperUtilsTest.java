package com.bird.fly.infra.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zhangruilong <zhangruilong03@kuaishou.com>
 * Created on 2024-04-29
 */
class ObjectMapperUtilsTest {
    @Test
    void getPropertyFromJsonStr() {
        String jsonStr = "{\"resultList\":{\"staticData\":[{\"code\":0,\"message\":\"请求成功\"},{\"code\":400,\"message\":\"请求失败\"}]}}";
        // 获取单个值
        String onePropertyFromJsonStr = ObjectMapperUtils.getPropertyFromJsonStr(jsonStr, "$.resultList.staticData[0].message", String.class);
        System.out.println(onePropertyFromJsonStr);
        // 获取多个值
        List<String> onePropertyFromJsonStr1 = ObjectMapperUtils.getPropertyFromJsonStr(jsonStr, "$.resultList.staticData[*].message", List.class);
        System.out.println(onePropertyFromJsonStr1);
        // 获取多个值
        List<String> onePropertyFromJsonStr2 = ObjectMapperUtils.getPropertyFromJsonStr(jsonStr, "$..message", List.class);
        System.out.println(onePropertyFromJsonStr2);
    }
}