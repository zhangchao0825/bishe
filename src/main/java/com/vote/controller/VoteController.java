package com.vote.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.vote.entity.*;
import com.vote.entity.vo.VoteDetail;
import com.vote.service.FileService;
import com.vote.service.UserChoiceService;
import com.vote.service.VoteItemService;
import com.vote.service.VoteService;
import com.vote.util.ResponseMsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/14 20:18
 */

@Controller
@RequestMapping("/vote")
public class VoteController {
    @Autowired
    private VoteService voteService;
    @Autowired
    private ResponseMsgUtil responseMsgUtil;
    @Autowired
    private FileService fileService;
    @Autowired
    private VoteItemService voteItemService;
    @Autowired
    private UserChoiceService userChoiceService;

    @ResponseBody
    @RequestMapping("/typeList")
    public String getAllType() {
        List<Type> typeList = voteService.findAllType();
        Object o = JSON.toJSON(typeList);
        return responseMsgUtil.resultDate(responseMsgUtil.SUCCESS_REQUEST, "查询投票类型成功", o, responseMsgUtil.STATUS_SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/addNewVote")
    @Transactional
    public String addNewVote(Long userId,
                             String[] images,
                             String videos,
                             String content,
                             String publishTime,
                             String finishTime,
                             String[] types,
                             String[] items) {
        Vote vote = new Vote();
        vote.setUserId(userId);
        vote.setContent(content);
        vote.setCreateTime(LocalDateTime.now());
        vote.setUpdateTime(LocalDateTime.now());
        vote.setStatus(1);
        vote.setFinishTime((finishTime==null||"".equals(finishTime)?LocalDateTime.now():LocalDateTime.parse(finishTime.replace("Z",""))));
        vote.setPublishTime((publishTime==null||"".equals(publishTime)?LocalDateTime.now():LocalDateTime.parse(publishTime.replace("Z",""))));
        StringBuilder typeSb = new StringBuilder();
        if (types.length > 0 && types != null) {
            for (String type : types) {
                String replace = type.replace("\"", "").replace("[", "").replace("]", "");
                typeSb.append(replace+",");
            }
        }
        vote.setType(typeSb.toString().substring(0,typeSb.length()-1));
        StringBuilder fileString = new StringBuilder();
        if (images.length!=0){
            for (String image : images) {
                if ("[]".equals(image)){
                    continue;
                }
                File file = new File();
                file.setCreateTime(LocalDateTime.now());
                file.setUpdateTime(LocalDateTime.now());
                file.setType("image");
                file.setPath(image.replace("\"","").replace("[","").replace("]","").replace("\\\\","\\"));
                Long id = fileService.saveFile(file);
                fileString.append(id+",");
            }
        }
        if (videos != null && !videos.isEmpty()&&!"[]".equals(videos)) {
            File file = new File();
            file.setCreateTime(LocalDateTime.now());
            file.setUpdateTime(LocalDateTime.now());
            file.setType("video");
            file.setPath(videos.replace("\"","").replace("[","").replace("]","").replace("\\\\","\\"));
            Long id = fileService.saveFile(file);
            fileString.append(id+",");
        }
        if (!"".equals(fileString.toString())){
            vote.setFiles(fileString.toString().substring(0,fileString.length()-1));
        }
        vote.setIsHot(false);
        Long voteId = voteService.save(vote);
        if (voteId != null&&items!=null&&items.length>0) {
            for (String item : items) {
                String substring = item.substring(item.indexOf("{\"name\":\"")+9, item.indexOf("\"}"));
                VoteItem voteItem = new VoteItem();
                voteItem.setVoteId(voteId);
                voteItem.setText(substring);
                voteItem.setUpdateTime(LocalDateTime.now());
                voteItem.setCreateTime(LocalDateTime.now());
                Long save = voteItemService.save(voteItem);
            }
            return responseMsgUtil.resultDate(responseMsgUtil.SUCCESS_REQUEST, "创建成功", null, responseMsgUtil.STATUS_SUCCESS);
        }
        return responseMsgUtil.resultDate(responseMsgUtil.FAIL_REQUEST, "服务端异常，创建失败，稍后重试", null, responseMsgUtil.STATUS_FAIL);
    }

    @ResponseBody
    @RequestMapping("/getHotVote")
    public String getHotVote(Long userId){
        List<VoteDetail> voteDetails = voteService.findHotVote(userId);
        return responseMsgUtil.resultDate(responseMsgUtil.SUCCESS_REQUEST, "查询成功", voteDetails, responseMsgUtil.STATUS_SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/getVoteList")
    public String getVoteList(Long userId,int pageNo,int pageSize,Long type){
        Map<String, Object> vote = voteService.findVote(userId, pageNo, pageSize, type);
        return responseMsgUtil.resultDate(responseMsgUtil.SUCCESS_REQUEST, "查询成功", vote, responseMsgUtil.STATUS_SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/addChoice")
    @Transactional
    public String addNewVote(UserChoice userChoice) {
        userChoice.setCreateTime(LocalDateTime.now());
        int row = userChoiceService.addChoice(userChoice);
        if (row > 0) {
            return responseMsgUtil.resultDate(responseMsgUtil.SUCCESS_REQUEST, "创建成功", null, responseMsgUtil.STATUS_SUCCESS);
        }else {
            return responseMsgUtil.resultDate(responseMsgUtil.FAIL_REQUEST, "服务端异常，创建失败，稍后重试", null, responseMsgUtil.STATUS_FAIL);
        }
    }

    @ResponseBody
    @RequestMapping("/like")
    @Transactional
    public String like(Long userId,Long voteId) {
        Boolean result = voteService.like(userId,voteId);
        if (result) {
            return responseMsgUtil.resultDate(responseMsgUtil.SUCCESS_REQUEST, "创建成功", null, responseMsgUtil.STATUS_SUCCESS);
        }else {
            return responseMsgUtil.resultDate(responseMsgUtil.FAIL_REQUEST, "服务端异常，创建失败，稍后重试", null, responseMsgUtil.STATUS_FAIL);
        }
    }
}
