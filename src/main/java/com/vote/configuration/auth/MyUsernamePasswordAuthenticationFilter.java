package com.vote.configuration.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vote.entity.MyUser;
import com.vote.service.auth.MyUserPreLoginServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 重写UsernamePasswordAuthenticationFilter过滤器
 */
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private MyUserPreLoginServer myUserLoginServer;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        //判断请求格式是否为json
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE) || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            //取authenticationBean
            Map<String, String> authenticationBean = null;
            //用try with resource，方便自动释放资源
            try (InputStream is = request.getInputStream()) {
                authenticationBean = mapper.readValue(is, Map.class);
            } catch (IOException e) {
                throw  new MyAuthenticationException(e.getMessage());
            }
            try {
                if (!authenticationBean.isEmpty()) {
                    //获得账号、密码
                    MyUser myUser = new MyUser();
                    myUser.setUsername(authenticationBean.get(SPRING_SECURITY_FORM_USERNAME_KEY));
                    myUser.setPassword(authenticationBean.get(SPRING_SECURITY_FORM_PASSWORD_KEY));
                    //可以验证账号、密码
                    if (myUserLoginServer.usernamePasswordLogin(myUser)) {
                        myUser.setUsername(authenticationBean.get(SPRING_SECURITY_FORM_USERNAME_KEY));
                        myUser.setPassword(authenticationBean.get(SPRING_SECURITY_FORM_PASSWORD_KEY));
                        authRequest = new UsernamePasswordAuthenticationToken(myUser.getUsername(), myUser.getPassword());
                        setDetails(request, authRequest);
                        return this.getAuthenticationManager().authenticate(authRequest);
                    }else {
                        throw new Exception("账户名密码不匹配");
                    }
                }
            } catch (Exception e) {
                throw new MyAuthenticationException(e.getMessage());
            }
        }else {
            try {
                throw new Exception("请求参数格式异常");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
