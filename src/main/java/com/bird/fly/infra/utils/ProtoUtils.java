package com.bird.fly.infra.utils;

import org.apache.commons.lang3.StringUtils;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-11
 * proto工具类
 */
public class ProtoUtils {
    /**
     * 将 proto格式的 Message 打印为一行输出到日志中
     */
    public static String toOneLine(Message message) {
        if (message == null) {
            return StringUtils.EMPTY;
        }
        try {
            return JsonFormat.printer().omittingInsignificantWhitespace().print(message);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }
}
