package com.yipage.leanmarketing.utils;


import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * token管理工具类
 */
@Component
public class TokenManagerUtil {

    private static RedisTemplate<Object, Object> redisTemplate;

    /**
     * 根据userId创建唯一的tooken
     *
     * @param userId
     */
    public static String createToken(Integer userId) {

        //创建一个唯一的token
        String token = userId + "_" + UUID.randomUUID().toString().replaceAll("-", "");

        String key = userId + "_token";
        //将token存入redis中
        redisTemplate.opsForValue().set(key, token, 7, TimeUnit.DAYS);

        return token;
    }

    /**
     * 注销Token
     */
    public static boolean clearToken(String token) {
        //验证token是否为空
        if (token == null || StringUtils.isEmpty(token)) {
            return false;
        }
        String[] arr = token.split("_");
        if (arr.length != 2) {
            return false;
        }
        String key = arr[0] + "_token";
        //检查一下是否存在这个token
        String redis_token = (String) redisTemplate.opsForValue().get(key);
        if (redis_token == null) {
            return false;
        }
        redisTemplate.delete(key);
        return true;
    }

    /**
     * 检查token是否正确
     */
    public static boolean checkToken(String token) {
        //验证token是否为空
        if (null == token || StringUtils.isEmpty(token)) {
            return false;
        }
        String[] arr = token.split("_");
        if (arr.length != 2) {
            return false;
        }
        String key = arr[0] + "_token";
        String redis_token = (String) redisTemplate.opsForValue().get(key);
        if (redis_token == null) {
            return false;
        }
        if (!redis_token.equals(token)) {
            return false;
        }
        //更新token时间
        redisTemplate.opsForValue().set(key, token, 7, TimeUnit.DAYS);
        return true;
    }

    public static Integer getUserId(String token) {

        if (null == token || StringUtils.isEmpty(token)) {
            return -1;
        }
        String[] arr = token.split("_");
        if (arr.length != 2) {
            return -1;
        }
        return Integer.parseInt(arr[0]);
    }

    public static Integer getType(String token) {

        if (null == token || StringUtils.isEmpty(token)) {
            return -1;
        }
        String[] arr = token.split("_");
        if (arr.length != 2) {
            return -1;
        }
        return Integer.parseInt(arr[1]);
    }

    @Resource
    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        TokenManagerUtil.redisTemplate = redisTemplate;
    }

}
