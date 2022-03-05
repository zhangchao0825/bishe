package com.vote;

import com.vote.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class VoteSystemApplicationTests {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisUtil redisUtil;

    @Test
    public void testRedis(){
        redisUtil.set("zhangchao","111");
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testPassword(){
        String encode = passwordEncoder.encode("123456");
        boolean matches = passwordEncoder.matches("123456", "$2a$10$GnJEiuYYn.LldROT1so66e52CAwc.luD525F4hM.fV07I7MKJaiPW");
        System.out.println(matches);
        System.out.println(encode);
    }
}
