package com.study.service;

import com.study.model.BoardDTO;

import java.sql.Timestamp;
import java.util.List;

/*
 todo : spring boot 에서 service 와 serviceImpl 을 나누는 이유를 생각
 */
public interface BoardService {

    /**
     * 목록 카운팅
     * @return
     * @throws Exception
     */
    public Integer getBoardCount(String searchId, int categoryNum, Timestamp startDate, Timestamp endDate);


    /**
     * list 보기 및 검색
     * @param
     * @return
     * @throws Exception
     */
    public List<BoardDTO> getBoardList(String searchId, int categoryNum, Timestamp startDate, Timestamp endDate, int pageNum, int pageSize);

    /**
     * 글보기
     * @param boardNum
     * @return
     */
    public BoardDTO viewBoardContent(int boardNum);

    /**
     * 글작성
     * @param boardDTO
     * @return
     * @throws Exception
     */
    public int writeBoardContent(BoardDTO boardDTO);

    /**
     * 글수정
     * @param
     * @return
     * @throws Exception
     */
    public int modifyBoardContent(BoardDTO boardDTO);

    /**
     * 글삭제
     * @param boardNum
     * @throws Exception
     */
    public void deleteBoardContent(int boardNum);

    /**
     * 조회수
     * @param boardNum
     * @throws Exception
     */
    public void updateHit(int boardNum);

}
