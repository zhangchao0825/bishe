package com.vote.dao;

import com.vote.entity.Comment;
import com.vote.entity.Vote;
import com.vote.entity.VoteItem;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/19 20:41
 */
@Mapper
public interface VoteMapper {
    int save(Vote vote);

    List<Vote> findHotVote(LocalDateTime now);

    List<Long> findUserChoice(Long userId);

    Integer findItemCount(Long itemId);

    List<Vote> findVote(LocalDateTime now,Long type);

    int findUserLike(Long userId, Long id);

    int findLikeCount(Long id);

    List<Comment> selectCommentTop(Long id);
}
