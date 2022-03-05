package com.vote.dao;

import com.vote.entity.VoteItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/19 20:51
 */
@Mapper
public interface VoteItemMapper {
    int save(VoteItem voteItem);

    List<VoteItem> findByVoteId(Long id);
}
