package com.study.backend.service;

import com.study.backend.model.FileDTO;
import com.study.backend.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

@RequiredArgsConstructor
@Service("FileService")
public class FileServiceImpl implements FileService {

    @Value("${spring.servlet.multipart.location}")
    private String saveFileUrl;

    private final FileMapper fileMapper;

    /**
     * 파일 추가
     *
     * @param boardNum
     * @param fileName
     * @param filePath
     * @throws IOException
     */
    @Override
    public void insertFile(int boardNum, String fileName, String filePath) throws IOException {

        FileDTO fileDTO = new FileDTO();
        fileDTO.setBoardNum(boardNum);
        fileDTO.setOriginalFileName(fileName);
        fileDTO.setSaveFileName(fileName);
        fileDTO.setFilePath(filePath);

        File fileSaveDir = new File(saveFileUrl);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        fileMapper.insertFile(fileDTO);
    }

    /**
     * 파일 전체 블러오기
     *
     * @param boardNum
     * @return
     */
    @Override
    public List<FileDTO> getFileList(int boardNum) {
        return fileMapper.getFileList(boardNum);
    }

    /**
     * 게시판 글 삭제시 파일 전체 삭제
     *
     * @param boardNum
     */
    @Override
    public void deleteAllFile(int boardNum) {
        fileMapper.deleteAllFile(boardNum);
    }

    /**
     * 게시판 글 수정시 파일 삭제
     *
     * @param boardNum
     * @param fileNum
     */
    @Override
    public void deleteSelectFile(int boardNum, int fileNum) {
        fileMapper.deleteSelectFile(boardNum, fileNum);
    }

    /**
     * 파일 다운로드
     *
     * @param boardNum 게시글의 식별자
     * @param fileNum
     * @return
     * @throws IOException
     */
    @Override
    public File downloadFile(int boardNum, int fileNum) throws IOException {

        FileDTO fileDTO = fileMapper.getFileNum(boardNum, fileNum);
        String filePath = fileDTO.getFilePath();

        if (!filePath.startsWith(saveFileUrl)) {
            throw new SecurityException("저장된 위치가 아닙니다.");
        }

        File fileDownload = new File(filePath);

        if (!fileDownload.exists() || !fileDownload.isFile()) {
            throw new FileNotFoundException("파일이 존재하지 않습니다." + filePath);
        }
        return fileDownload;
    }
}
