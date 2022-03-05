package com.vote.dao;

import com.vote.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/12 12:28
 */

@Mapper
public interface UserRoleMapper {
    int insertUserRole(UserRole userRole);
}
