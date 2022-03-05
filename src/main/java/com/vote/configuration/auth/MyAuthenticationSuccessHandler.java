package com.vote.configuration.auth;

import com.google.gson.Gson;
import com.vote.dao.UserMapper;
import com.vote.entity.auth.User;
import com.vote.util.JwtTokenUtil;
import com.vote.util.ResponseMsgUtil;
import com.vote.util.TokenCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * 登录成功操作
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private Gson gson;
    @Autowired
    private ResponseMsgUtil responseMsgUtil;
    @Autowired
    private TokenCacheUtil tokenCacheUtil;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        //取得账号信息
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenCacheUtil.getTokenFromCache(userDetails.getUsername());
        try {
            //如果token过期或者初次登录
            if(token ==null||jwtTokenUtil.isTokenExpired(token)) {
                //如果token为空，则去创建一个新的token
                jwtTokenUtil = new JwtTokenUtil();
                token = jwtTokenUtil.generateToken(userDetails);
                //把新的token存储到缓存中
                tokenCacheUtil.setToken(userDetails.getUsername(), token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        User user = (User) userDetails;
        HashMap<String, Object> loginSuccessResult = new HashMap<>();
        loginSuccessResult.put(jwtTokenUtil.getHeader(),token);
        loginSuccessResult.put("id",user.getId());
        loginSuccessResult.put("username",user.getUsername());
        loginSuccessResult.put("photo",user.getPhoto());
        loginSuccessResult.put("sex",user.getSex());
        loginSuccessResult.put("authorization",userDetails.getAuthorities());
        loginSuccessResult.put("status",user.getStatus());
        userMapper.updateLoginTime(user.getUsername(), LocalDateTime.now());
        responseMsgUtil.sendSuccessMsg(responseMsgUtil.SUCCESS_REQUEST,"登录成功",loginSuccessResult,response);
    }
}
