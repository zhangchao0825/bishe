package com.vote.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/26 12:19
 */
@Data
public class UserChoice {
    private Long itemId;
    private Long userId;
    private String ipAddress;
    private String address;
    private LocalDateTime createTime;
}
