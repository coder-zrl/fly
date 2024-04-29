package com.bird.fly.infra.utils;

import com.jayway.jsonpath.JsonPath;

import java.util.List;

/**
 * @author zhangruilong <zhangruilong03@kuaishou.com>
 * Created on 2024-04-29
 */
public class ObjectMapperUtils {
    /**
     * 从Json格式字符串中获取某字段的值
     * e.p.1 获取单个值 $.resultList.staticData[0].message
     * e.p.2 获取多个值 $.resultList.staticData[*].message、$..message
     */
    public static <T> T getPropertyFromJsonStr(String jsonStr, String jsonPath, Class<T> clazz) {
        return JsonPath.parse(jsonStr).read(JsonPath.compile(jsonPath));
    }

    public static void main(String[] args) {
        String jsonStr = "{\"resultList\":{\"staticData\":[{\"code\":0,\"message\":\"请求成功\"},{\"code\":400,\"message\":\"请求失败\"}]}}";
        // 获取单个值
        String onePropertyFromJsonStr = getPropertyFromJsonStr(jsonStr, "$.resultList.staticData[0].message", String.class);
        System.out.println(onePropertyFromJsonStr);
        // 获取多个值
        List<String> onePropertyFromJsonStr1 = getPropertyFromJsonStr(jsonStr, "$.resultList.staticData[*].message", List.class);
        System.out.println(onePropertyFromJsonStr1);
        // 获取多个值
        List<String> onePropertyFromJsonStr2 = getPropertyFromJsonStr(jsonStr, "$..message", List.class);
        System.out.println(onePropertyFromJsonStr2);
    }
}
