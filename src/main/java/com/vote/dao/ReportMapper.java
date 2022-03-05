package com.vote.dao;

import com.vote.entity.Report;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/21 22:11
 */

@Mapper
public interface ReportMapper {
    int save(Report report);

}
