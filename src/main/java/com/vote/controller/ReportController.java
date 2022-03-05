package com.vote.controller;

import com.vote.entity.Report;
import com.vote.service.ReportService;
import com.vote.util.ResponseMsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/21 22:07
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private ResponseMsgUtil responseMsgUtil;

    @RequestMapping("/submitReport")
    public String submitReport(Report report){
        report.setCreateTime(LocalDateTime.now());
        boolean result = reportService.addNewReport(report);
        if (result) {
            return responseMsgUtil.resultDate(responseMsgUtil.SUCCESS_REQUEST, "举报成功", null, responseMsgUtil.STATUS_SUCCESS);
        }else {
            return responseMsgUtil.resultDate(responseMsgUtil.FAIL_REQUEST, "服务端异常，举报失败，稍后重试", null, responseMsgUtil.STATUS_FAIL);
        }
    }
}
