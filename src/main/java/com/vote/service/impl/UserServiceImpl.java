package com.vote.service.impl;

import com.vote.dao.UserMapper;
import com.vote.dao.UserRoleMapper;
import com.vote.entity.MyUser;
import com.vote.entity.UserRole;
import com.vote.entity.auth.User;
import com.vote.service.UserService;
import com.vote.util.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/12 1:13
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoderUtil encoderUtil;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public boolean userIsExists(String username) {
        MyUser myUser = userMapper.selectByUsername(username);
        return myUser!=null?true:false;
    }

    @Override
    @Transactional
    public boolean addNewUser(MyUser myUser) {
        myUser.setPassword(encoderUtil.encode(myUser.getPassword()));
        myUser.setCreateTime(LocalDateTime.now());
        myUser.setUpdateTime(LocalDateTime.now());
        myUser.setStatus(true);
        int row = userMapper.insertNewUser(myUser);
        if (row>0) {
            UserRole userRole = new UserRole(myUser.getId(), 3L, LocalDateTime.now(), LocalDateTime.now());
            int userRoleRow = userRoleMapper.insertUserRole(userRole);
            return userRoleRow>0?true:false;
        }
        return false;
    }

    @Override
    public User findUserInfo(String username) {
        MyUser myUser = userMapper.selectByUsername(username);
        List<String> permissionList = userMapper.selectUserPermission(username);
        StringBuilder sb = new StringBuilder();
        for (String permission : permissionList) {
            sb.append(permission+",");
        }
        String permission = sb.toString().substring(0, sb.length() - 1);

        //把查询出来的密码进行解析,或直接把 password 放到构造方法中。
        return new User(myUser.getId(),username, myUser.getPassword(),myUser.getPhoto(),myUser.getSex(),myUser.getLoginTime(),myUser.getCreateTime(),myUser.getUpdateTime(),myUser.getStatus(), AuthorityUtils.commaSeparatedStringToAuthorityList(permission));
    }

    @Override
    public boolean updateUsernameSex(MyUser myUser) {
        Integer row = userMapper.updateUsernameSex(myUser);
        return row>0?true:false;
    }

    @Override
    public boolean updatePhoto(MyUser myUser) {
        Integer row = userMapper.updatePhoto(myUser);
        return row>0?true:false;
    }

    @Override
    public boolean validatePass(MyUser myUser, String password) {
        MyUser resultUser = userMapper.selectById(myUser.getId());
        if (encoderUtil.matches(myUser.getPassword(),resultUser.getPassword())){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updatePass(MyUser myUser) {
        myUser.setPassword(encoderUtil.encode(myUser.getPassword()));
        Integer row = userMapper.updatePass(myUser);
        return row>0?true:false;
    }
}
