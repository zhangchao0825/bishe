package com.vote.service;

import com.vote.entity.MyUser;
import com.vote.entity.auth.User;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/12 1:13
 */
public interface UserService {
    public boolean userIsExists(String username);

    boolean addNewUser(MyUser myUser);

    User findUserInfo(String username);

    boolean updateUsernameSex(MyUser myUser);

    boolean updatePhoto(MyUser myUser);

    boolean validatePass(MyUser myUser, String password);

    boolean updatePass(MyUser myUser);
}
