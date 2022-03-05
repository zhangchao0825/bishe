package com.vote.service.auth;

import com.vote.dao.UserMapper;
import com.vote.entity.MyUser;
import com.vote.entity.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/3 20:26
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1. 查询数据库判断用户名是否存在，如果不存在抛出相应异常
        MyUser myUser = userMapper.selectByUsername(username);
        if (myUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        List<String> permissionList = userMapper.selectUserPermission(username);
        StringBuilder sb = new StringBuilder();
        for (String permission : permissionList) {
            sb.append(permission+",");
        }
        String permission = sb.toString().substring(0, sb.length() - 1);

        //把查询出来的密码进行解析,或直接把 password 放到构造方法中。
        return new User(myUser.getId(),username, myUser.getPassword(),myUser.getPhoto(),myUser.getSex(),myUser.getLoginTime(),myUser.getCreateTime(),myUser.getUpdateTime(),myUser.getStatus(), AuthorityUtils.commaSeparatedStringToAuthorityList(permission));
    }
}

