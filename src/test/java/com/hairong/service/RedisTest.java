package com.hairong.service;

import com.hairong.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void test() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // 增
        valueOperations.set("hairongString", "dog");
        valueOperations.set("hairongInt", 1);
        valueOperations.set("hairongDouble", 2.0);
        User user = new User();
        user.setId(1L);
        user.setUsername("hairong");
        valueOperations.set("hairongUser", user);
        // 查
        Object hairong = valueOperations.get("hairongString");
        Assertions.assertTrue("dog".equals((String)hairong));
        hairong = valueOperations.get("hairongInt");
        Assertions.assertTrue(1 == (Integer) hairong);
        hairong = valueOperations.get("hairongDouble");
        Assertions.assertTrue(2.0 == (Double) hairong);
        System.out.println(valueOperations.get("hairongUser"));
        valueOperations.set("hairongString", "dog");
        redisTemplate.delete("hairongString");
    }
}
