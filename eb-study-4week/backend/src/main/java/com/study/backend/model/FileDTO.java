package com.study.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FileDTO {
    
    int fileNum;
    int boardNum;
    String saveFileName;
    String originalFileName;
    String filePath;
}
