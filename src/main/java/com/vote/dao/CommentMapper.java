package com.vote.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/26 23:07
 */
@Mapper
public interface CommentMapper {

    int findCount(Long id);
}
