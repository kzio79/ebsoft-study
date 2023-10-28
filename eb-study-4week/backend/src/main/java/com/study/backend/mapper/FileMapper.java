package com.study.backend.mapper;

import com.study.backend.model.FileDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {

    /**
     * 파일 등록
     *
     * @throws Exception
     */
    public void insertFile(FileDTO fileDTO);

    /**
     * 파일 리스트
     *
     * @param boardNum
     * @return
     * @throws Exception
     */
    public List<FileDTO> getFileList(int boardNum);

    /**
     * 파일 전체 삭제
     */
    public void deleteAllFile(int boardNum);

    /**
     * 선택 파일 삭제
     *
     * @param boardNum
     * @param fileNum
     */
    public void deleteSelectFile(int boardNum, int fileNum);

    /**
     * 파일 번호 불러오기
     *
     * @param boardNum
     * @param fileNum
     * @return
     */
    public FileDTO getFileNum(int boardNum, int fileNum);

    /**
     * 파일 카운트
     *
     * @param boardNum
     * @return
     */
    public Integer fileCount(int boardNum);

}
