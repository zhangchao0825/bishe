package com.vote.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/7 11:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class File implements Serializable {
    private static final long serialVersionUID = -5731668015481015436L;

    private Long id;
    private String path;
    private String type;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
