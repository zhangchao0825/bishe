package com.vote.configuration.auth;

import com.vote.util.JwtTokenUtil;
import com.vote.util.TokenCacheUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截器
 */
@Component
public class MyOncePerRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private TokenCacheUtil tokenCacheUtil;
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String headerToken = request.getHeader(jwtTokenUtil.getHeader());
        //有token验证token
        if (StringUtils.hasLength(headerToken)&&!headerToken.equals("null")&&!headerToken.equals("undefined")) {
            //postMan测试时，自动假如的前缀，要去掉。
            //判断令牌是否过期，默认是一周
            //比较好的解决方案是：
            //登录成功获得token后，将token存储到数据库（redis）
            //将数据库版本的token设置过期时间为15~30分钟
            //如果数据库中的token版本过期，重新刷新获取新的token
            //注意：刷新获得新token是在token过期时间内有效。
            //如果token本身的过期（1周），强制登录，生成新token。
            boolean check = false;
            try {
                check = jwtTokenUtil.isTokenExpired(headerToken);
            } catch (Exception e) {
                throw new Exception("令牌已过期，请重新登录。");
//                new Throwable(""+e.getMessage());
            }
            //验证token是否合法
            if (!check){
                //通过令牌获取用户名称
                String username = jwtTokenUtil.getUsernameFromToken(headerToken);
                //判断用户名是否为空
                if (username != null){
                    //获取刷新的token
                    String refreshToken = jwtTokenUtil.refreshToken(headerToken);
                    //刷新缓存中的token
                    tokenCacheUtil.freshenTokenCache(username,refreshToken);
                    //将刷新的token放入请求头中
                    response.setHeader(jwtTokenUtil.getHeader(),refreshToken);
                }
                //判断用户不为空，且SecurityContextHolder授权信息还是空的
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    //通过用户信息得到UserDetails
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    //验证令牌有效性
                    boolean validata = false;
                    try {
                        validata = jwtTokenUtil.validateToken(headerToken, userDetails);
                    }catch (Exception e) {
                        new Throwable("验证token无效:"+e.getMessage());
                    }
                    if (validata) {
                        // 将用户信息存入 authentication，方便后续校验
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                );
                        //
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        // 将 authentication 存入 ThreadLocal，方便后续获取用户信息
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
}
