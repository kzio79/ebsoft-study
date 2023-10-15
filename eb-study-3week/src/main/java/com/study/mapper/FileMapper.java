package com.study.mapper;

import com.study.model.FileDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {

    /**
     * file upload
     * @throws Exception
     */
    public void updateFileList(FileDTO fileDTO);

    /**
     * file getLIst
     * @param boardNum
     * @return
     * @throws Exception
     */
    public List<FileDTO> getFileList(int boardNum);

    /**
     * file delete
     */
    public void deleteAllFile(int boardNum);

    /**
     * file select delete
     * @param boardNum
     * @param fileNum
     */
    public void deleteSelectFile(int boardNum, int fileNum);

    /**
     * find fileNum
     * @param boardNum
     * @param fileNum
     * @return
     */
    public FileDTO getFileNum(int boardNum, int fileNum);

    public Integer fileCount(int boardNum);

}
