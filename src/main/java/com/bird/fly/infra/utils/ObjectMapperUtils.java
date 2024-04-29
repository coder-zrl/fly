package com.bird.fly.infra.utils;

import com.jayway.jsonpath.JsonPath;

/**
 * 序列化与反序列化相关工具类
 *
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
}
