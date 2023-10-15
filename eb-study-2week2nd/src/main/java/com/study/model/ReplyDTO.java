package com.study.model;

import java.sql.Timestamp;

public class ReplyDTO {

    int replyNum;
    int boardNum;
    String content;
    Timestamp replyDate;

    public ReplyDTO() {}

    public ReplyDTO(int replyNum, int boardNum, String content, Timestamp replyDate) {
        this.replyNum = replyNum;
        this.boardNum = boardNum;
        this.content = content;
        this.replyDate = replyDate;
    }

    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }

    public int getBoardNum() {
        return boardNum;
    }

    public void setBoardNum(int boardNum) {
        this.boardNum = boardNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Timestamp replyDate) {
        this.replyDate = replyDate;
    }

    @Override
    public String toString() {
        return "ReplyDTO{" +
                "replyNum=" + replyNum +
                ", boardNum=" + boardNum +
                ", content='" + content + '\'' +
                ", replyDate=" + replyDate +
                '}';
    }
}
