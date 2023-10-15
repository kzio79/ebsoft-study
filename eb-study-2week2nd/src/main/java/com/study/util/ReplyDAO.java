package com.study.util;

import com.study.connection.JdbcUtil;
import com.study.connection.NetworkConn;
import com.study.model.ReplyDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ReplyDAO {

    private  static final ReplyDAO instance = new ReplyDAO();
    public static ReplyDAO getInstance() {
        return instance;
    }

    /**
     * reply 불러오기
     */

    public List<ReplyDTO> getReplyList(int boardNum) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<ReplyDTO> replyList = new ArrayList<>();

        String replySql = "SELECT * FROM tb_reply WHERE board_num=? ORDER BY reply_date DESC";

        try {

            conn = NetworkConn.getConnection();
            pstmt = conn.prepareStatement(replySql);

            pstmt.setInt(1, boardNum);

            rs = pstmt.executeQuery();

            while (rs.next()){

                ReplyDTO replyDTO = new ReplyDTO();
                replyDTO.setReplyNum(rs.getInt("reply_num"));
                replyDTO.setBoardNum(boardNum);
                replyDTO.setContent(rs.getString("content"));
                replyDTO.setReplyDate(rs.getTimestamp("reply_date"));

                replyList.add(replyDTO);
            }


        } catch ( Exception e){
            e.printStackTrace();
        } finally {

            JdbcUtil.close(conn,pstmt,rs);
        }
        return replyList;
    }

    /**
     * reply 저장
     */
    public void updateReply(int boardNum, String content){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String replyUpdateSql = "INSERT INTO tb_reply(board_num, content) VALUES(?,?)";

        try {

            conn = NetworkConn.getConnection();
            pstmt = conn.prepareStatement(replyUpdateSql);

            pstmt.setInt(1, boardNum);
            pstmt.setString(2, content);

            pstmt.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            JdbcUtil.close(conn,pstmt,rs);
        }
    }
}
