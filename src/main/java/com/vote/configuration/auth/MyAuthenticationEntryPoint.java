package com.vote.configuration.auth;

import com.vote.util.ResponseMsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 身份校验失败处理器，如 token 错误
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    ResponseMsgUtil responseMsgUtil;
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        responseMsgUtil.sendFailMsg(responseMsgUtil.TOKEN_ERROR,"认证失败:访问此资源需要完全身份验证(token无效或者过期)!请从新登录",response);

    }
}
