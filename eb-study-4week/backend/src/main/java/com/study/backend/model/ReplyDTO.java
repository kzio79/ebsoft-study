package com.study.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReplyDTO {

    int replyNum;
    int boardNum;
    String content;
    Timestamp replyDate;
}
