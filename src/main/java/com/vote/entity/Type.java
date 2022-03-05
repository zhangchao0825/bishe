package com.vote.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.rmi.server.UID;
import java.time.LocalDateTime;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/7 11:42
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type implements Serializable {
    private static final long serialVersionUID = -1725714278764577551L;

    private Long id;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
