package com.vote.service.impl;

import com.vote.dao.UserChoiceMapper;
import com.vote.entity.UserChoice;
import com.vote.service.UserChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/26 12:22
 */
@Service
public class UserChoiceServiceImpl implements UserChoiceService {
    @Autowired
    private UserChoiceMapper userChoiceMapper;

    @Override
    public int addChoice(UserChoice userChoice) {
        return userChoiceMapper.save(userChoice);
    }
}
