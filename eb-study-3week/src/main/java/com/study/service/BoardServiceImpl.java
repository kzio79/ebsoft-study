package com.study.service;

import com.study.mapper.BoardMapper;
import com.study.mapper.FileMapper;
import com.study.model.BoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.sql.Timestamp;
import java.util.List;

@Service("boardService")
public class BoardServiceImpl implements BoardService{
    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private FileMapper fileMapper;

    /**
     * 목록 카운팅
     * @return
     * @throws Exception
     */

    @Override
    public Integer getBoardCount
            (String searchId,int categoryNum, Timestamp startDate, Timestamp endDate) {
        return boardMapper.getBoardCount(searchId,categoryNum,startDate,endDate);
    }

    /**
     * 검색
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public List<BoardDTO> getBoardList
    (String searchId, int categoryNum, Timestamp startDate, Timestamp endDate, int pageNum, int pageSize) {

        int startPage = (pageNum -1 ) * pageSize;

        List<BoardDTO> getBoardList = boardMapper.getBoardList(searchId, categoryNum, startDate, endDate, startPage, pageSize);
        for(BoardDTO boardDTO : getBoardList){
            int fileCount = fileMapper.fileCount(boardDTO.getBoardNum());
            boardDTO.setFileCount(fileCount);
        }
        return getBoardList;
    }

    /**
     * 글보기
     * @param boardNum
     * @return
     */
    @Override
    public BoardDTO viewBoardContent(int boardNum) {
        return boardMapper.viewBoardContent(boardNum);
    }

    /**
     * 글작성
     * @param boardDTO
     * @return
     * @throws Exception
     */
    @Override
    public int writeBoardContent(BoardDTO boardDTO) {
        return boardMapper.writeBoardContent(boardDTO);
    }

    /**
     * 글수정
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public int modifyBoardContent(BoardDTO boardDTO) {
        return boardMapper.modifyBoardContent(boardDTO);
    }

    /**
     * 글삭제
     * @param boardNum
     * @throws Exception
     */
    @Override
    public void deleteBoardContent(int boardNum) {
        boardMapper.deleteBoardContent(boardNum);

    }

    /**
     * 조회수
     * @param boardNum
     * @throws Exception
     */
    @Override
    public void updateHit(int boardNum) {
        boardMapper.updateHit(boardNum);
    }
}
