package com.vote.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyUser implements Serializable {
    private static final long serialVersionUID = -8173435412956060796L;
    private Long id;
    private String username;
    private String password;
    private String photo;
    private Boolean sex;
    private LocalDateTime loginTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean status;

    public MyUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
