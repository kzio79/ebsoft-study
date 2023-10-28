package com.study.backend.model;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BoardDTO {

    @NonNull
    int boardNum;
    @NonNull
    int categoryNum;
    @NonNull
    String title;
    @NonNull
    String writer;
    @NonNull
    String pw;
    @NonNull
    String content;
    @NonNull
    Timestamp writeDate;
    @NonNull
    Timestamp modifyDate;
    @NonNull
    int hit;
    @NonNull
    int fileCount;

    int pageNum = 1;
    int pageSize = 10;
    String searchId;
    String startDate;
    String endDate;
}
