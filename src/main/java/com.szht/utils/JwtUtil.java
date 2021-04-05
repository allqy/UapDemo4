package com.szht.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
@Data
@Component
@ConfigurationProperties(prefix = "uap.jwt")
public class JwtUtil {
    /**
     * 加密密钥
     */
    private String secret;

    /**
     * 过期时间(秒)
     */
    private Integer expire;

    /**
     * 请求头
     */
    private String header;

    /**
     * map 传入的数据
     * @param map
     * @return
     */
    public String getToken(Map<String,String> map) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, expire);
        JWTCreator.Builder create = JWT.create();
        map.forEach((k,v)->{
            create.withClaim(k, v);
        });
        String token=create
                .withHeader((Map<String, Object>) new HashMap<String, Object>().put("tye", "jwt"))
                //发布token时间
                .withIssuedAt(new Date(System.currentTimeMillis()))
                //指定过期时间
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(secret));
        return token;
    }

    /**
     * 验证tokenn
     */
    public DecodedJWT verify(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        DecodedJWT verify = jwtVerifier.verify(token);
        return verify;
    }

    /**
     * 根据key获取map的值  只能String
     * @param token
     * @param key
     * @return
     */
    public String getUsername(String token,String key) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(key).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
