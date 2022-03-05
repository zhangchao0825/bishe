package com.vote.dao;

import com.vote.entity.File;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/19 15:30
 */
@Mapper
public interface FileMapper {

    int save(File file);

    File findById(long id);
}
