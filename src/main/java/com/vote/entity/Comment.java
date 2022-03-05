package com.vote.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/26 23:05
 */
@Data
public class Comment implements Serializable {
    private static final long serialVersionUID = -140368695799822408L;

    private Long id;
    private Long voteId;
    private Long userId;
    private String content;
    private Long parentId;
    private List<Comment> commentList;
    private LocalDateTime createTime;

}
