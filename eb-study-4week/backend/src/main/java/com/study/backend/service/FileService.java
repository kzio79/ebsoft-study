package com.study.backend.service;

import com.study.backend.model.FileDTO;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;


public interface FileService {

    /**
     * 파일 등록
     *
     * @throws Exception
     */
//    public void updateFileList(FileDTO fileDTO, Part filePart) throws IOException;
    public void insertFile(int boardNum, String fileName, String filePath) throws IOException;


    /**
     * 파일 리스트 불러오기
     *
     * @throws Exception
     */
    public List<FileDTO> getFileList(int boardNum);

    /**
     * 전체 파일 삭제
     *
     * @param boardNum
     */
    public void deleteAllFile(int boardNum);

    /**
     * 선택 파일 삭제
     *
     * @param boardNum
     */
    public void deleteSelectFile(int boardNum, int fileNum);

    /**
     * 파일 다운로드
     *
     * @param boardNum
     * @param fileNum
     */
    public File downloadFile(int boardNum, int fileNum) throws IOException;

}
