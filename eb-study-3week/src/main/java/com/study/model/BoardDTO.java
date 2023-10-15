package com.study.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    int fileCount;
}
