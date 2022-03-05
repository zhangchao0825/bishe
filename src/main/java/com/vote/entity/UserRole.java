package com.vote.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/12 12:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole implements Serializable {
    private static final long serialVersionUID = -7876503846194371551L;
    private Long userId;
    private Long roleId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
