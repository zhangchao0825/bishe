package com.vote.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/26 20:39
 */
@Data
public class Like implements Serializable {
    private static final long serialVersionUID = 7493264744091531451L;
    private Long id;
    private Long voteId;
    private Long userId;
    private LocalDateTime createTime;

    public Like() {
    }

    public Like(Long voteId, Long userId, LocalDateTime createTime) {
        this.voteId = voteId;
        this.userId = userId;
        this.createTime = createTime;
    }
}
