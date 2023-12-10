package com.bird.fly.infra.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.bird.fly.infra.error.ServiceErrors;
import com.bird.fly.infra.exception.ApiServiceException;
import com.bird.fly.infra.model.JwtSubjectUserModel;
import com.bird.fly.infra.properties.JwtProperties;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author coder-zrl@qq.com
 * Created on 2023-12-06
 * jwt工具，默认没开启全局验证
 */
@Slf4j
@Component
public class JwtUtils {
    private static JwtProperties jwtProperties;
    private static JWTVerifier verifier;

    @Autowired
    public JwtUtils(JwtProperties jwtProperties) {
        JwtUtils.jwtProperties = jwtProperties;
        JwtUtils.verifier = JWT.require(Algorithm.HMAC256(jwtProperties.getSecret())).build();
    }

    /**
     * 生成用户token,设置token超时时间
     */
    public static String createToken(JwtSubjectUserModel user) {
        //过期时间
        Date expireDate = new Date(System.currentTimeMillis() + jwtProperties.getExpireTime() * 1000);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        return JWT.create()
                // Part1. 添加头部
                .withHeader(map)
                // Part2. 生成payload，明文显示，不要添加私密信息
                .withSubject(JSONUtil.toJsonStr(user)) // 主题，代表这个Token的主体，一般是个json格式的字符串
                .withExpiresAt(expireDate) // 超时设置,设置过期的日期
                .withIssuedAt(new Date()) // 签发时间
                .withClaim("claim", "test")
                // Part3. 生成签名
                .sign(Algorithm.HMAC256(jwtProperties.getSecret()));
    }


    /**
     * 检验token并解析token
     */
    public static Boolean verifyToken(HttpServletRequest request) {
        if (request == null) {
            return false;
        }
        String token = request.getHeader("Authorization");
        return verifyToken(token);
    }

    public static Boolean verifyToken(String token) {
        if (StringUtils.isEmpty(token)) {
            throw ServiceErrors.user_not_login.createException();
        }
        try {
            verifier.verify(token);
            return true;
        } catch (AlgorithmMismatchException e) {
            throw new ApiServiceException(14, "JWT的签名算法与预期的算法不匹配");
        } catch (SignatureVerificationException e) {
            throw new ApiServiceException(14, "JWT的签名无法被验证r");
        } catch (TokenExpiredException e) {
            throw new ApiServiceException(14, "JWT已经过期");
        } catch (InvalidClaimException e) {
            throw new ApiServiceException(14, "JWT的某个声明（claim）无效，例如期望的发行人（issuer）与实际的发行人不匹配");
        } catch (JWTDecodeException e) {
            throw new ApiServiceException(14, "JWT的格式不正确");
        } catch (Exception e) {
            throw ServiceErrors.un_know.createException();
        }
    }

    /**
     * 解析token中的用户Id
     *
     * @param request request
     * @return userId
     */
    @NotNull
    public static Long parseUserIdFromToken(HttpServletRequest request) {
        if (request == null) {
            return 0L;
        }
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        try {
            String subject = verifier.verify(token).getSubject();
            if (StringUtils.isEmpty(subject)) {
                return 0L;
            }
            JwtSubjectUserModel user = JSONUtil.toBean(subject, JwtSubjectUserModel.class);
            Long userId = user.getUserId();
            request.setAttribute("userId", userId);
            return userId;
        } catch (Exception e) {
            log.error("verifyToken error", e);
        }
        return 0L;
    }
}