package com.study.util;

import com.study.connection.JdbcUtil;
import com.study.connection.NetworkConn;
import com.study.model.BoardDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DataBase 접근 로직
 */
public class BoardDAO {

    private static final BoardDAO instance = new BoardDAO();

    public static BoardDAO getInstance() {
        return instance;
    }

    /**
     * 목록 총 건수
     * todo:try-catch : catch부분에서 ...., 변수명 신경, 구조화
     *
     */
    public int totalBoardCount(String searchId, int category, Timestamp startDate, Timestamp endDate){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int result = 0;

        StringBuilder totalHitSql = new StringBuilder("SELECT COUNT(*) AS total_count FROM tb_board");

        List<String> conditions = new ArrayList<>();

        if(searchId != null && !searchId.isEmpty()){
            conditions.add("( title LIKE ? OR writer LIKE ?)");
        }

        if(category > 0){
            conditions.add("category_num=?");
        }

        if(startDate != null && endDate != null){
            conditions.add("write_date BETWEEN ? AND ? ");
        } else if (startDate != null) {
            conditions.add("write_date >= ?");
        } else if (endDate != null) {
            conditions.add("write_date <= ?");
        }

        if(!conditions.isEmpty()) {
            totalHitSql.append(" WHERE ");
            totalHitSql.append(String.join(" AND ", conditions));
        }

        try {

            conn = NetworkConn.getConnection();
            pstmt = conn.prepareStatement(totalHitSql.toString());

            int index = 1;

            if(searchId != null && !searchId.isEmpty()){
                pstmt.setString(index++, "%"+searchId+"%");
                pstmt.setString(index++, "%"+searchId+"%");
            }

            if(category > 0){
                pstmt.setInt(index++, category);
            }

            if(startDate != null && endDate != null){
                pstmt.setTimestamp(index++, startDate);
                pstmt.setTimestamp(index++, endDate);
            } else if (startDate != null) {
                pstmt.setTimestamp(index++, startDate);
            } else if (endDate != null) {
                pstmt.setTimestamp(index++, endDate);
            }

            rs = pstmt.executeQuery();

            if(rs.next()){

                result = rs.getInt("total_count");

            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            JdbcUtil.close(conn,pstmt,rs);
        }

        return result;
    }


    /**
     * 목록 불러오기
     */

    public List<BoardDTO> getBoardList(int pageNum, int pageSize){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<BoardDTO> boardList = new ArrayList<>();

        String listSql = "SELECT * FROM tb_board ORDER BY board_num DESC LIMIT ?, ?";

        try {

            conn = NetworkConn.getConnection();
            pstmt = conn.prepareStatement(listSql);

            int startIndex = (pageNum -1)* pageSize;
            pstmt.setInt(1, startIndex);
            pstmt.setInt(2, pageSize);

            rs = pstmt.executeQuery();

            while (rs.next()){

                BoardDTO boardDTO = new BoardDTO();

                boardDTO.setBoardNum(rs.getInt("board_num"));
                boardDTO.setCategoryNum(rs.getInt("category_num"));
                boardDTO.setTitle(rs.getString("title"));
                boardDTO.setWriter(rs.getString("writer"));
                boardDTO.setPw(rs.getString("pw"));
                boardDTO.setContent(rs.getString("content"));
                boardDTO.setWriterDate(rs.getTimestamp("write_date"));
                boardDTO.setModifyDate(rs.getTimestamp("modify_date"));
                boardDTO.setHit(rs.getInt("hit"));

                boardList.add(boardDTO);

            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            JdbcUtil.close(conn,pstmt,rs);
        }

        return boardList;
    }

    /**
     * 검색
     * todo: 검색, 카운팅 : 하나의 코드로 리팩토링
     */

    public List<BoardDTO> selectContent(String searchId,
                                        int category, Timestamp startDate, Timestamp endDate,
                                        int pageNum, int pageSize){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<BoardDTO> boardList = new ArrayList<>();

        StringBuilder selectSql = new StringBuilder("SELECT * FROM tb_board");

        List<String> conditions = new ArrayList<>();

        if(searchId != null && !searchId.isEmpty()){
            conditions.add("(title LIKE ? OR writer LIKE ?)");
        }

        if(category > 0){
            conditions.add("category_num=?");
        }

        if(startDate != null && endDate != null){
            conditions.add("write_date BETWEEN ? AND ? ");
        } else if (startDate != null) {
            conditions.add("write_date >= ?");
        } else if (endDate != null) {
            conditions.add("write_date <= ?");
        }

        if(!conditions.isEmpty()) {
            selectSql.append(" WHERE ");
            selectSql.append(String.join(" AND ", conditions));
        }

        selectSql.append(" ORDER BY board_num DESC LIMIT ?, ?");

        try {

            conn = NetworkConn.getConnection();
            pstmt = conn.prepareStatement(selectSql.toString());

            int index = 1;

            if(searchId != null && !searchId.isEmpty()){
                pstmt.setString(index++, "%"+searchId+"%");
                pstmt.setString(index++, "%"+searchId+"%");
            }

            if(category > 0){
                pstmt.setInt(index++, category);
            }

            if(startDate != null && endDate != null){
                pstmt.setTimestamp(index++, startDate);
                pstmt.setTimestamp(index++, endDate);
            } else if (startDate != null) {
                pstmt.setTimestamp(index++, startDate);
            } else if (endDate != null) {
                pstmt.setTimestamp(index++, endDate);
            }

            pstmt.setInt(index++, (pageNum - 1) * pageSize);
            pstmt.setInt(index++, pageSize);

            rs = pstmt.executeQuery();

            while (rs.next()){

                BoardDTO boardDTO = new BoardDTO();

                boardDTO.setBoardNum(rs.getInt("board_num"));
                boardDTO.setCategoryNum(rs.getInt("category_num"));
                boardDTO.setTitle(rs.getString("title"));
                boardDTO.setWriter(rs.getString("writer"));
                boardDTO.setPw(rs.getString("pw"));
                boardDTO.setWriterDate(rs.getTimestamp("write_date"));
                boardDTO.setModifyDate(rs.getTimestamp("modify_date"));
                boardDTO.setHit(rs.getInt("hit"));

                boardList.add(boardDTO);

            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            JdbcUtil.close(conn,pstmt,rs);
        }

        return boardList;
    }

    /**
     * 글보기
     */
    public BoardDTO viewBoardContent(int boardNum){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        BoardDTO boardDTO = null;

        String contentSql = "SELECT * FROM tb_board WHERE board_num=?";

        try {
            conn = NetworkConn.getConnection();
            pstmt = conn.prepareStatement(contentSql);
            pstmt.setInt(1, boardNum);

            rs = pstmt.executeQuery();

            if(rs.next()){

                boardDTO = new BoardDTO();
                boardDTO.setTitle(rs.getString("title"));
                boardDTO.setWriter(rs.getString("writer"));
                boardDTO.setPw(rs.getString("pw")); // 사용하질 않을 코드는 작성하지 않는다
                boardDTO.setWriterDate(rs.getTimestamp("write_date"));
                boardDTO.setModifyDate(rs.getTimestamp("modify_date"));
                boardDTO.setCategoryNum(rs.getInt("category_num"));
                boardDTO.setHit(rs.getInt("hit"));
                boardDTO.setContent(rs.getString("content"));

            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            JdbcUtil.close(conn,pstmt,rs);
        }
        return boardDTO;
    }

    /**
     * 글쓰기
     * todo : pw 암호화는 필수, 복호화가 되지 않는 pw를 작성
     */
    public int writeBoardContent(int categoryNum, String title, String writer, String pw, String content ){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String writeSql = "INSERT INTO tb_board(category_num, title, writer, pw, content)" +
                "VALUES(?,?,?,?,?)";

        try {

            if(writer != null && !writer.isEmpty() || pw != null && !pw.isEmpty() ||
                    title != null && !title.isEmpty() || content != null && !content.isEmpty()) {

                conn = NetworkConn.getConnection();
                pstmt = conn.prepareStatement(writeSql, Statement.RETURN_GENERATED_KEYS);

                pstmt.setInt(1, categoryNum);
                pstmt.setString(2, title);
                pstmt.setString(3, writer);
                pstmt.setString(4, pw);
                pstmt.setString(5, content);

                pstmt.executeUpdate();

                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException();
                    }
                } catch (SQLException se){
                    se.printStackTrace();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return 0;

        } finally {
            JdbcUtil.close(conn,pstmt,rs);
        }
        return -1;
    }

    /**
     * 글수정
     *
     * @return
     */
    public int modifyBoardContent(String writer, String pw, String title, String content, int boardNum){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String updateSql = "UPDATE tb_board SET writer=?, pw=?, title=?, content=?, modify_date=now() WHERE board_num=?";

        try {

            conn = NetworkConn.getConnection();
            pstmt = conn.prepareStatement(updateSql);

            if(writer != null && !writer.isEmpty() || pw != null && !pw.isEmpty() ||
                    title != null && !title.isEmpty() || content != null && !content.isEmpty()){
                pstmt.setString(1, writer);
                pstmt.setString(2, pw);
                pstmt.setString(3, title);
                pstmt.setString(4, content);
                pstmt.setInt(5, boardNum);

                return pstmt.executeUpdate();
            }

        } catch (Exception e){
            e.printStackTrace();
            return 0;
        } finally {
            JdbcUtil.close(conn,pstmt,rs);
        }

        return -1;
    }

    /**
     * 글삭제
     *
     * @return
     */
    public void deleteBoardContent(int boardNum){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String deleteSql = "DELETE FROM tb_board WHERE board_num=?";

        try {

            conn = NetworkConn.getConnection();
            pstmt = conn.prepareStatement(deleteSql);

            pstmt.setInt(1, boardNum);

            pstmt.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            JdbcUtil.close(conn,pstmt,rs);
        }
    }

    /**
     * 조회수 update
     */

    public void updateBoardHit(int boardNum){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String updatHitSql = "UPDATE tb_board SET hit = hit + 1 WHERE board_num=?";

        try {

            conn = NetworkConn.getConnection();
            pstmt = conn.prepareStatement(updatHitSql);

            pstmt.setInt(1, boardNum);

            pstmt.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            JdbcUtil.close(conn,pstmt,rs);
        }
    }
}


