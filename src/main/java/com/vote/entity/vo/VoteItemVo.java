package com.vote.entity.vo;

import com.vote.entity.VoteItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/26 10:02
 */
@Data
@AllArgsConstructor
public class VoteItemVo {
    private String question;
    private boolean showResult;
    private List<VoteItem> answers;
}
