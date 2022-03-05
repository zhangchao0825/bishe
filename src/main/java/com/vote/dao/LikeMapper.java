package com.vote.dao;

import com.vote.entity.Like;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/26 20:55
 */
@Mapper
public interface LikeMapper {

    int deleteLike(Long userId,Long voteId);

    int addLike(Like like);
}
