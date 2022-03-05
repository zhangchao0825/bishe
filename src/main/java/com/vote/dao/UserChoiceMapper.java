package com.vote.dao;

import com.vote.entity.UserChoice;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/26 12:23
 */
@Mapper
public interface UserChoiceMapper {

    int save(UserChoice userChoice);

}
