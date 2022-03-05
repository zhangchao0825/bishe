package com.vote.service.impl;

import com.vote.dao.VoteItemMapper;
import com.vote.entity.VoteItem;
import com.vote.service.VoteItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/19 20:50
 */
@Service
public class VoteItemServiceImpl implements VoteItemService {
    @Autowired
    private VoteItemMapper voteItemMapper;

    @Override
    public Long save(VoteItem voteItem) {
        int row = voteItemMapper.save(voteItem);
        return voteItem.getValue();
    }
}
