package com.vote.configuration.auth;

import com.vote.util.ResponseMsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限校验处理器
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Autowired
    ResponseMsgUtil responseMsgUtil;
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        responseMsgUtil.sendFailMsg(responseMsgUtil.PERMISSIONS_ERROR,"权限不足",response);
    }
}
