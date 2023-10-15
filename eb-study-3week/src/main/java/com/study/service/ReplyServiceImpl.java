package com.study.service;

import com.study.mapper.ReplyMapper;
import com.study.model.ReplyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("replyService")
public class ReplyServiceImpl implements ReplyService{

    @Autowired
    ReplyMapper replyMapper;

    @Override
    public List<ReplyDTO> getReplyList(int boardNum) {
        return replyMapper.getReplyList(boardNum);
    }

    @Override
    public void writeReply(int boardNum, String content) {
        replyMapper.updateReply(boardNum,content);
    }

    @Override
    public void deleteReply(int boardNum) {
        replyMapper.deleteReply(boardNum);
    }
}
