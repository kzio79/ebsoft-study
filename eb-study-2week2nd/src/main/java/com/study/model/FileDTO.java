package com.study.model;


import java.sql.Blob;

public class FileDTO {
    int fileNum;
    int boardNum;
    String fileName;

    Blob fileContent;

    String filePath;

    public FileDTO() {
    }

    public FileDTO(int fileNum, int boardNum, String fileName, Blob fileContent, String filePath) {
        this.fileNum = fileNum;
        this.boardNum = boardNum;
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.filePath = filePath;
    }

    public int getFileNum() {
        return fileNum;
    }

    public void setFileNum(int fileNum) {
        this.fileNum = fileNum;
    }

    public int getBoardNum() {
        return boardNum;
    }

    public void setBoardNum(int boardNum) {
        this.boardNum = boardNum;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public java.sql.Blob getFileContent() {
        return fileContent;
    }

    public void setFileContent(java.sql.Blob fileContent) {
        this.fileContent = fileContent;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}