package com.study.backend.mapper;

import com.study.backend.model.BoardDTO;
import com.study.backend.model.CategoryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface BoardMapper {


    /**
     * 목록 카운팅
     *
     * @return
     * @throws Exception
     */

    public Integer getBoardCount(@Param("searchId") String searchId,
                                 @Param("categoryNum") int categoryNum,
                                 @Param("startDate") Timestamp startDate,
                                 @Param("endDate") Timestamp endDate);

    /**
     * 리스트 불러오기,검색
     *
     * @param
     * @return
     * @throws Exception
     */
    public List<BoardDTO> getBoardList(@Param("searchId") String searchId,
                                       @Param("categoryNum") int categoryNum,
                                       @Param("startDate") Timestamp startDate,
                                       @Param("endDate") Timestamp endDate,
                                       @Param("startPage") int startPage,
                                       @Param("pageSize") int pageSize);

    /**
     * 글보기
     *
     * @param boardNum
     * @return
     */
    public BoardDTO viewBoardContent(int boardNum);

    /**
     * 글작성
     *
     * @param boardDTO
     * @return
     * @throws Exception
     */
    public int writeBoardContent(BoardDTO boardDTO);

    /**
     * 글수정
     *
     * @param
     * @return
     * @throws Exception
     */
    public int modifyBoardContent(BoardDTO boardDTO);

    /**
     * 글삭제
     *
     * @param boardNum
     * @throws Exception
     */
    public void deleteBoardContent(int boardNum);

    /**
     * 조회수
     *
     * @param boardNum
     * @throws Exception
     */
    public void updateHit(int boardNum);

    public CategoryDTO getCategoryNum(int categoryNum);

    public String validPwCheck(int boardNum);


}
