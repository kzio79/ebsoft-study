package com.study.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    int categoryNum;

    int boardNum;

    String saveFileName;
    String originalFileName;

    String filePath;
}
