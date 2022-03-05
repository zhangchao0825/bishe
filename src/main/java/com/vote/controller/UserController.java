package com.vote.controller;

import com.alibaba.fastjson.JSON;
import com.vote.entity.MyUser;
import com.vote.entity.auth.User;
import com.vote.service.UserService;
import com.vote.util.FilesUtil;
import com.vote.util.ResponseMsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/3 20:26
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private FilesUtil filesUtil;

    @Autowired
    private ResponseMsgUtil responseMsgUtil;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/photoUpload", headers = "content-type=multipart/*", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            String filePath = filesUtil.getAbsolutePath() + LocalDate.now(); //文件存放文件夹
            //判断文件夹是否存在不存在的话创建文件夹
            File fpath = new File(filePath);
            if (!fpath.exists()) {
                fpath.mkdirs();
            }
            String fileName = file.getOriginalFilename();
            //为了不重复给文件起的新名称
            String newFileName = filesUtil.getRandomFileName(fileName);
            String path = filePath + "/" + newFileName;
            File upLoadFile = new File(path);
            String fileType = "";
            FileOutputStream out = null;
            try {
                fileType = filesUtil.getFileType(upLoadFile);
                out = new FileOutputStream(upLoadFile);
                out.write(file.getBytes());
                out.flush();
            } catch (Exception e) {
                return responseMsgUtil.resultDate(responseMsgUtil.FAIL_REQUEST, "上传失败" + e.getMessage(), null, responseMsgUtil.STATUS_FAIL);
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return responseMsgUtil.resultDate(responseMsgUtil.SUCCESS_REQUEST, "上传成功", upLoadFile, responseMsgUtil.STATUS_SUCCESS);
        } else {
            return responseMsgUtil.resultDate(responseMsgUtil.FAIL_REQUEST, "上传失败文件为空", null, responseMsgUtil.STATUS_FAIL);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/register")
    public String register(MyUser myUser) {
        boolean flag = userService.userIsExists(myUser.getUsername());
        if (flag) {
            return responseMsgUtil.resultDate(responseMsgUtil.FAIL_REQUEST, "用户已存在", null, responseMsgUtil.STATUS_FAIL);
        }
        boolean insertResult = userService.addNewUser(myUser);
        if (insertResult) {
            return responseMsgUtil.resultDate(responseMsgUtil.SUCCESS_REQUEST, "注册成功", null, responseMsgUtil.STATUS_SUCCESS);
        }else {
            return responseMsgUtil.resultDate(responseMsgUtil.FAIL_REQUEST, "服务端异常，注册失败，稍后重试", null, responseMsgUtil.STATUS_FAIL);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/userExists")
    public String userExists(String username) {
        boolean userExists = userService.userIsExists(username);
        return responseMsgUtil.resultDate(responseMsgUtil.SUCCESS_REQUEST, "查询用户是否存在成功", userExists, responseMsgUtil.STATUS_SUCCESS);
    }

    @ResponseBody
    @RequestMapping(value = "/getUserInfo")
    public String getUserInfo(String username) {
        User userInfo = userService.findUserInfo(username);
        userInfo.setPassword("");
        Object o = JSON.toJSON(userInfo);
        return responseMsgUtil.resultDate(responseMsgUtil.SUCCESS_REQUEST, "查询用户信息成功", o, responseMsgUtil.STATUS_SUCCESS);
    }

    @ResponseBody
    @RequestMapping(value = "/updateUsernameSex")
    public String updateUsernameSex(Long userId,boolean sex) {
        MyUser myUser = new MyUser();
        myUser.setId(userId);
        myUser.setSex(sex);
        boolean result = userService.updateUsernameSex(myUser);
        if (result) {
            return responseMsgUtil.resultDate(responseMsgUtil.SUCCESS_REQUEST, "修改用户基本信息成功", null, responseMsgUtil.STATUS_SUCCESS);
        }else {
            return responseMsgUtil.resultDate(responseMsgUtil.FAIL_REQUEST, "服务端异常，修改失败，稍后重试", null, responseMsgUtil.STATUS_FAIL);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/updatePhoto")
    public String updatePhoto(Long userId,String userPhoto) {
        MyUser myUser = new MyUser();
        myUser.setId(userId);
        myUser.setPhoto(userPhoto);
        boolean result = userService.updatePhoto(myUser);
        if (result) {
            return responseMsgUtil.resultDate(responseMsgUtil.SUCCESS_REQUEST, "修改用户头像成功", null, responseMsgUtil.STATUS_SUCCESS);
        }else {
            return responseMsgUtil.resultDate(responseMsgUtil.FAIL_REQUEST, "服务端异常，修改失败，稍后重试", null, responseMsgUtil.STATUS_FAIL);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/updatePass")
    public String updatePass(Long userId,String oldPass,String password) {
        MyUser myUser = new MyUser();
        myUser.setId(userId);
        myUser.setPassword(oldPass);
        boolean result = userService.validatePass(myUser,password);
        if (result) {
            myUser.setPassword(password);
            boolean updatePass = userService.updatePass(myUser);
            if (updatePass) {
                return responseMsgUtil.resultDate(responseMsgUtil.SUCCESS_REQUEST, "修改用户密码成功", null, responseMsgUtil.STATUS_SUCCESS);
            }else {
                return responseMsgUtil.resultDate(responseMsgUtil.FAIL_REQUEST, "服务端异常，修改失败，稍后重试", null, responseMsgUtil.STATUS_FAIL);
            }
        }else {
            return responseMsgUtil.resultDate(responseMsgUtil.FAIL_REQUEST, "原密码错误", null, responseMsgUtil.STATUS_FAIL);
        }
    }
}
