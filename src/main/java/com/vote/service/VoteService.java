package com.vote.service;

import com.vote.entity.Type;
import com.vote.entity.Vote;
import com.vote.entity.vo.VoteDetail;

import java.util.List;
import java.util.Map;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/14 20:20
 */
public interface VoteService {
    List<Type> findAllType();

    Long save(Vote vote);

    List<VoteDetail> findHotVote(Long userId);

    Map<String,Object> findVote(Long userId, int pageNo, int pageSize, Long type);

    Boolean like(Long userId, Long voteId);
}
