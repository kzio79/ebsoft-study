package com.study.service;

import com.study.mapper.FileMapper;
import com.study.model.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("FileService")
public class FileServiceImpl implements FileService{

    @Autowired
    FileMapper fileMapper;

    @Override
    public void updateFileList(FileDTO fileDTO) {
        fileMapper.updateFileList(fileDTO);
    }

    @Override
    public List<FileDTO> getFileList(int boardNum) {
        return fileMapper.getFileList(boardNum);
    }

    @Override
    public void deleteAllFile(int boardNum) {
        fileMapper.deleteAllFile(boardNum);
    }

    @Override
    public void deleteSelectFile(int boardNum, int fileNum) {
        fileMapper.deleteSelectFile(boardNum, fileNum);
    }

    @Override
    public FileDTO getFileNum(int boardNum, int fileNum) {
        return fileMapper.getFileNum(boardNum, fileNum);
    }

}
