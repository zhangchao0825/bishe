package com.vote.service.impl;

import com.vote.dao.ReportMapper;
import com.vote.entity.Report;
import com.vote.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/21 22:08
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Override
    public boolean addNewReport(Report report) {
        int row = reportMapper.save(report);
        return row>0?true:false;
    }
}
