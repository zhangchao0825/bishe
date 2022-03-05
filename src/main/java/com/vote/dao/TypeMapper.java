package com.vote.dao;

import com.vote.entity.Type;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/14 20:21
 */
@Mapper
public interface TypeMapper {
    List<Type> selectList();

    Type findById(long id);
}
