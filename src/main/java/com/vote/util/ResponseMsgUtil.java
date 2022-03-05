package com.vote.util;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 响应信息工具类
 * @author cpms
 */
@Component
public class ResponseMsgUtil {
    public final String SUCCESS_REQUEST = "200";
    public final String TOKEN_ERROR = "4000";
    public final String PERMISSIONS_ERROR = "401";
    public final String FAIL_REQUEST = "5000";
    public final String STATUS_SUCCESS = "success";
    public final String STATUS_FAIL = "fail";
    /**
     * 返回成功的处理信息
     * @param msg 返回前端的提示
     * @param data 返回前端的数据
     * @param response 响应
     */
    public  void sendSuccessMsg(String code,String msg, Object data, HttpServletResponse response) throws IOException {
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        map.put("msg",msg);
        map.put("state",STATUS_SUCCESS);
        if (data!=null){
            map.put("data",data);
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(new Gson().toJson(map));
        out.flush();
        out.close();
    }

    /**
     * 返回失败的处理信息
     * @param msg 返回前端的提示信息
     * @param response 响应
     */
    public  void sendFailMsg(String code,String msg ,HttpServletResponse response) throws IOException {
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        map.put("msg",msg);
        map.put("state",STATUS_FAIL);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out =response.getWriter();
        out.write(new Gson().toJson(map));
        out.flush();
        out.close();
    }

    /**
     * 请求的统一返回
     * @param code
     * @param message
     * @param date
     * @return
     */
    public String resultDate (String code,String message,Object data,String status){
        HashMap<String, Object> result = new HashMap<>();
        result.put("code",code);
        result.put("message",message);
        result.put("data",data);
        result.put("status",status);
        return new Gson().toJson(result);
    }
}
