package com.vote.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/7 11:35
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vote implements Serializable {
    private static final long serialVersionUID = 6515770484528021046L;

    private Long id;
    private Long userId;
    private String files;
    private String content;
    private LocalDateTime publishTime;
    private LocalDateTime finishTime;
    private Boolean isHot;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    //0未发  1 发布  2下架
    private int status;
    private String type;
}
