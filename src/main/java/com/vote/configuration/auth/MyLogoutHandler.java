package com.vote.configuration.auth;

import com.vote.util.JwtTokenUtil;
import com.vote.util.TokenCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 退出Handler
 */
@Component
public class MyLogoutHandler  implements LogoutHandler {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    TokenCacheUtil tokenCacheUtil;
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        String headerToken = request.getHeader(jwtTokenUtil.getHeader());
        if (!StringUtils.isEmpty(headerToken)) {
            tokenCacheUtil.deleteTokenFromCache(jwtTokenUtil.getUsernameFromToken(headerToken));
            SecurityContextHolder.clearContext();
        }
    }
}
