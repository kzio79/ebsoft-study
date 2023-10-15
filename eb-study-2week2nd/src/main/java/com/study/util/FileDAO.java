package com.study.util;

import com.study.connection.JdbcUtil;
import com.study.connection.NetworkConn;
import com.study.model.FileDTO;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FileDAO {

    private  static final FileDAO instance = new FileDAO();

    public static FileDAO getInstance() {
        return instance;
    }

    /**
     * file 불러오기
     */

    public List<FileDTO> getFileList(int boardNum){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<FileDTO> fileList = new ArrayList<>();

        String fileSql = "SELECT * FROM tb_file WHERE board_num=?";

        try {

            conn = NetworkConn.getConnection();
            pstmt = conn.prepareStatement(fileSql);

            pstmt.setInt(1, boardNum);

            rs = pstmt.executeQuery();

            while (rs.next()){

                FileDTO fileDTO = new FileDTO();
                fileDTO.setFileName(rs.getString("file_name"));
                fileDTO.setBoardNum(boardNum);
                fileDTO.setFilePath(rs.getString("file_path"));

                fileList.add(fileDTO);
            }
        } catch ( Exception e){
            e.printStackTrace();
        } finally {

            JdbcUtil.close(conn,pstmt,rs);
        }
        return fileList;
    }



    /**
     * file 저장
     */

    public void updateFile(int boardNum, String fileName,String filePath){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String writeSql = "INSERT INTO tb_file(board_num, file_name, file_path)" +
                "VALUES(?,?,?)";

        try {

            conn = NetworkConn.getConnection();
            pstmt = conn.prepareStatement(writeSql);

            pstmt.setInt(1,boardNum);
            pstmt.setString(2, fileName);
            pstmt.setString(3, filePath);

            int result = pstmt.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            JdbcUtil.close(conn,pstmt,rs);
        }
    }
}
