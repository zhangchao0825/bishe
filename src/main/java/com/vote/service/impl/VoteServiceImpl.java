package com.vote.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vote.dao.*;
import com.vote.entity.*;
import com.vote.entity.vo.*;
import com.vote.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/14 20:20
 */
@Service
public class VoteServiceImpl implements VoteService {
    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private VoteMapper voteMapper;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private VoteItemMapper voteItemMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Type> findAllType() {
        List<Type> typeList = typeMapper.selectList();
        return typeList;
    }

    @Override
    public Long save(Vote vote) {
        int row = voteMapper.save(vote);
        return vote.getId();
    }

    @Override
    public List<VoteDetail> findHotVote(Long userId) {
        List<Vote> votes = voteMapper.findHotVote(LocalDateTime.now());
        if (votes.size() > 5) {
            votes = votes.subList(0,5);
        }
        List<VoteDetail> voteDetails = new ArrayList<>();
        for (Vote vote : votes) {
            VoteDetail voteDetail = new VoteDetail();
            voteDetail.setCommentCount(commentMapper.findCount(vote.getId()));
            List<File> fileList = new ArrayList<>();
            for (String fileId : vote.getFiles().split(",")) {
                File file = fileMapper.findById(Long.parseLong(fileId));
                fileList.add(file);
            }
            voteDetail.setId(vote.getId());
            voteDetail.setUser(userMapper.selectById(vote.getUserId()));
            voteDetail.setContent(vote.getContent());
            voteDetail.setFiles(fileList);
            voteDetail.setPublishTime(JSON.toJSONString(vote.getPublishTime()));
            voteDetail.setFinishTime(JSON.toJSONString(vote.getFinishTime()));
            voteDetail.setIsHot(vote.getIsHot());
            voteDetail.setStatus(vote.getStatus());
            List<Type> typeList = new ArrayList<>();
            for (String typeId : vote.getType().split(",")) {
                Type type = typeMapper.findById(Long.parseLong(typeId));
                typeList.add(type);
            }
            voteDetail.setTypeList(typeList);
            List<VoteItem> voteItemList = voteItemMapper.findByVoteId(vote.getId());
            voteDetail.setOptions(new VoteItemVo("What's your choice?",false,voteItemList));
            if (userId != null && voteDetail.getOptions().getAnswers()!=null) {
                List<Long> userChoice = voteMapper.findUserChoice(userId);
                for (Long aLong : userChoice) {
                    for (VoteItem voteItem : voteDetail.getOptions().getAnswers()) {
                        if (voteItem.getValue() == aLong){
                            voteDetail.setUserChoice(aLong);
                            voteDetail.getOptions().setShowResult(true);
                        }
                        voteItem.setVotes(voteMapper.findItemCount(voteItem.getValue()));
                    }
                }
                int likeCount = voteMapper.findUserLike(userId,vote.getId());
                voteDetail.setIsLike(likeCount>0?true:false);
            }

            voteDetail.setLikeCount(voteMapper.findLikeCount(vote.getId()));
            for (VoteItem voteItem : voteDetail.getOptions().getAnswers()) {
                voteItem.setVotes(voteMapper.findItemCount(voteItem.getValue()));
            }
            if (vote.getFinishTime().isBefore(LocalDateTime.now())){
                voteDetail.getOptions().setShowResult(true);
            }
            List<Comment> commentList = voteMapper.selectCommentTop(vote.getId());
            for (Comment comment : commentList) {
//                comment
            }
            voteDetails.add(voteDetail);
        }
        return voteDetails;
    }

    @Override
    public Map<String,Object> findVote(Long userId, int pageNo, int pageSize, Long type) {
        PageHelper.startPage(pageNo,pageSize);
        List<Vote> votes = voteMapper.findVote(LocalDateTime.now(),type);
        PageInfo<Vote> votePageInfo = new PageInfo<>(votes);
        List<VoteDetail> voteDetails = new ArrayList<>();
        for (Vote vote : votes) {
            VoteDetail voteDetail = new VoteDetail();
            voteDetail.setCommentCount(commentMapper.findCount(vote.getId()));
            List<File> fileList = new ArrayList<>();
            for (String fileId : vote.getFiles().split(",")) {
                File file = fileMapper.findById(Long.parseLong(fileId));
                fileList.add(file);
            }
            voteDetail.setId(vote.getId());
            voteDetail.setUser(userMapper.selectById(vote.getUserId()));
            voteDetail.setContent(vote.getContent());
            voteDetail.setFiles(fileList);
            voteDetail.setPublishTime(JSON.toJSONString(vote.getPublishTime()));
            voteDetail.setFinishTime(JSON.toJSONString(vote.getFinishTime()));
            voteDetail.setIsHot(vote.getIsHot());
            voteDetail.setStatus(vote.getStatus());
            List<Type> typeList = new ArrayList<>();
            for (String typeId : vote.getType().split(",")) {
                Type typeObj = typeMapper.findById(Long.parseLong(typeId));
                typeList.add(typeObj);
            }
            voteDetail.setTypeList(typeList);
            List<VoteItem> voteItemList = voteItemMapper.findByVoteId(vote.getId());
            voteDetail.setOptions(new VoteItemVo("What's your choice?",false,voteItemList));
            if (userId != null && voteDetail.getOptions().getAnswers()!=null) {
                List<Long> userChoice = voteMapper.findUserChoice(userId);
                for (Long aLong : userChoice) {
                    for (VoteItem voteItem : voteDetail.getOptions().getAnswers()) {
                        if (voteItem.getValue() == aLong){
                            voteDetail.setUserChoice(aLong);
                            voteDetail.getOptions().setShowResult(true);
                        }
                        voteItem.setVotes(voteMapper.findItemCount(voteItem.getValue()));
                    }
                }
                int likeCount = voteMapper.findUserLike(userId,vote.getId());
                voteDetail.setIsLike(likeCount>0?true:false);
                voteDetail.setLikeCount(voteMapper.findLikeCount(vote.getId()));
            }
            for (VoteItem voteItem : voteDetail.getOptions().getAnswers()) {
                voteItem.setVotes(voteMapper.findItemCount(voteItem.getValue()));
            }
            if (vote.getFinishTime().isBefore(LocalDateTime.now())){
                voteDetail.getOptions().setShowResult(true);
            }
            voteDetails.add(voteDetail);
        }
        Map<String,Object> result = new HashMap<>();
        result.put("votes",voteDetails);
        result.put("hasNextPage",votePageInfo.isHasNextPage());
        return result;
    }

    @Override
    public Boolean like(Long userId, Long voteId) {
        int likeCount = voteMapper.findUserLike(userId,voteId);
        int row;
        if (likeCount > 0) {
            row = likeMapper.deleteLike(userId,voteId);
        }else {
            row = likeMapper.addLike(new Like(voteId,userId,LocalDateTime.now()));
        }
        return row>0;
    }
}
