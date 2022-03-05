package com.vote.service.impl;

import com.vote.dao.FileMapper;
import com.vote.entity.File;
import com.vote.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/19 15:28
 */

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileMapper fileMapper;
    @Override
    public Long saveFile(File file) {
        int row = fileMapper.save(file);
        return file.getId();
    }
}
