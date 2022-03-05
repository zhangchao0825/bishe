package com.vote.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/21 22:03
 */
@Data
public class Report {
    private Long id;
    private Long voteId;
    private Long userId;
    private String reason;
    private String result;
    private LocalDateTime createTime;
}
