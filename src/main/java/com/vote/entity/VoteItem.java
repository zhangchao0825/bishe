package com.vote.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/7 11:43
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteItem implements Serializable {
    private static final long serialVersionUID = 5815186384277904921L;

    private Long value;
    private String text;
    private Long voteId;
    private Integer votes = 0;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
