package com.bird.fly.infra.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import cn.hutool.core.util.IdUtil;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-11
 * traceId 工具类，在TraceFilter和Traceable中会被使用
 */
public class TraceIdUtils {
    public static final String TRACE_ID_KEY = "TraceId";

    public static void injectTraceId() {
        injectTraceId(IdUtil.fastSimpleUUID());
    }

    public static void injectTraceId(String traceId) {
        if (StringUtils.isEmpty(traceId)) {
            traceId = IdUtil.fastSimpleUUID();
        }
        MDC.put(TRACE_ID_KEY, traceId);
    }

    public static void removeTraceId() {
        MDC.remove(TRACE_ID_KEY);
    }
}
