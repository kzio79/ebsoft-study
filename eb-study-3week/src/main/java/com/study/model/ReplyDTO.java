package com.study.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    int replyNum;
    int boardNum;
    String content;
    Timestamp replyDate;
}