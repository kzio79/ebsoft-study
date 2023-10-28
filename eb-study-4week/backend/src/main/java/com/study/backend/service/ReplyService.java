package com.study.backend.service;

import com.study.backend.model.ReplyDTO;

import java.util.List;

public interface ReplyService {

    /**
     * reply 불러오기
     *
     * @param boardNum
     * @return
     * @throws Exception
     */
    public List<ReplyDTO> getReplyList(int boardNum);

    /**
     * reply 작성
     *
     * @param boardNum
     * @param content
     * @return
     * @throws Exception
     */
    public void writeReply(int boardNum, String content);

    /**
     * reply 삭제
     */
    public void deleteReply(int boardNum);

}
