package com.study.service;

import com.study.model.FileDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileService {

    /**
     * file upload
     * @throws Exception
     */
    public void updateFileList(FileDTO fileDTO);

    /**
     * file getLIst
     * @throws Exception
     */
    public List<FileDTO> getFileList(int boardNum);

    /**
     * file all delete
     * @param boardNum
     */
    public void deleteAllFile(int boardNum);

    /**
     * file all delete
     * @param boardNum
     */
    public void deleteSelectFile(int boardNum, int fileNum);

    /**
     * file getNum
     * @param boardNum
     * @return
     */
    public FileDTO getFileNum(int boardNum, int fileNum);

}
