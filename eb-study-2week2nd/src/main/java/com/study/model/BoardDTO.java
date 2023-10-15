package com.study.model;

import java.sql.Timestamp;

/**
 * getter, setter
 */
public class BoardDTO {

    int boardNum;
    int categoryNum;
    String title;
    String writer;
    String pw;
    String content;
    Timestamp writeDate;
    Timestamp modifyDate;
    int hit;

    public BoardDTO() {}

    public BoardDTO(int boardNum, int categoryNum, String title, String writer, String pw, String content, Timestamp writeDate, Timestamp modifyDate, int hit) {
        this.boardNum = boardNum;
        this.categoryNum = categoryNum;
        this.title = title;
        this.writer = writer;
        this.pw = pw;
        this.content = content;
        this.writeDate = writeDate;
        this.modifyDate = modifyDate;
        this.hit = hit;
    }

    public int getBoardNum() {
        return boardNum;
    }

    public void setBoardNum(int boardNum) {
        this.boardNum = boardNum;
    }

    public int getCategoryNum() {
        return categoryNum;
    }

    public void setCategoryNum(int categoryNum) {
        this.categoryNum = categoryNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getWriterDate() {
        return writeDate;
    }

    public void setWriterDate(Timestamp writerDate) {
        this.writeDate = writerDate;
    }

    public Timestamp getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Timestamp modifyDate) {
        this.modifyDate = modifyDate;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    @Override
    public String toString() {
        return "BoardDAO{" +
                "boardNum=" + boardNum +
                ", categoryNum=" + categoryNum +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", pw='" + pw + '\'' +
                ", content='" + content + '\'' +
                ", writeDate=" + writeDate +
                ", modifyDate=" + modifyDate +
                ", hit=" + hit +
                '}';
    }
}
