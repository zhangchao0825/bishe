package com.vote.entity.vo;

import com.vote.entity.File;
import com.vote.entity.MyUser;
import com.vote.entity.Type;
import com.vote.entity.VoteItem;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/20 14:06
 */
@Data
public class VoteDetail implements Serializable {

    private static final long serialVersionUID = -6299886956809544015L;

    private Long id;
    private MyUser user;
    private List<File> files;
    private String content;
    private String publishTime;
    private String finishTime;
    private Boolean isHot;
    //0未发  1 发布  2下架
    private int status;
    private List<Type> typeList;
    private VoteItemVo options;
    private Long userChoice;
    private Boolean isLike;
    private int likeCount = 0;
    private int commentCount = 0;
}