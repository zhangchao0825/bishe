package com.vote.service.auth;

import com.vote.dao.UserMapper;
import com.vote.entity.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MyUserPreLoginServer {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder encoder;

    public boolean usernamePasswordLogin(MyUser myUser){
        //查询自己维护的用户表中是否存在
        MyUser resultMyUser = userMapper.selectByUsername(myUser.getUsername());

        if (resultMyUser!= null ){
            if (encoder.matches(myUser.getPassword(),resultMyUser.getPassword())){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }
}
