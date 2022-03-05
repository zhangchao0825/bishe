package com.vote.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenCacheUtil {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    private  Cache<String,String> cache = CacheBuilder.newBuilder().build();

    /**
     * 保存
     * @param token
     */
    public  void setToken(String username,String token) {
        cache.put(jwtTokenUtil.getHeader()+username,token);
    }

    /**
     * 取
     * @return
     */
    public  String getTokenFromCache(String username){
        return cache.getIfPresent(jwtTokenUtil.getHeader()+username);
    }

    /**
     * 删除
     */
    public void deleteTokenFromCache(String username){
        cache.invalidate(jwtTokenUtil.getHeader()+username);
    }
    /**
     * 刷新
     */
    public void freshenTokenCache(String username,String token){
        deleteTokenFromCache(username);
        setToken(username,token);
    }
}
