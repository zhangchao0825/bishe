package com.vote.dao;

import com.vote.entity.MyUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 查找用户的用户名和密码在user表中是否存在
     *
     * @param username 用户名
     * @return 用户对象
     */
    MyUser selectByUsername(String username);

    /**
     * 查询用户的权限信息
     * @param username 用户名
     * @return 用户的权限
     */
    List<String> selectUserPermission(String username);

    int insertNewUser(MyUser myUser);

    void updateLoginTime(@Param("username") String username,@Param("loginTime") LocalDateTime loginTime);

    Integer updateUsernameSex(MyUser myUser);

    Integer updatePhoto(MyUser myUser);

    MyUser selectById(Long id);

    Integer updatePass(MyUser myUser);
}
