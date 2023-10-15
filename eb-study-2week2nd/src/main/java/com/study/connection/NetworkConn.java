package com.study.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NetworkConn {

    static final String DB_URL = "jdbc:mysql://localhost:3306/ebsoft_study4?useUnicode=true&characterEncoding=UTF-8";
    static final String USER = "ebsoft4";
    static final String PASS = "ebsoft";

    public static Connection getConnection() throws Exception{

        Connection conn = null;

        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


        } catch (ClassNotFoundException ex) {
            throw new SQLException("JDBC 드라이버를 찾을 수 없습니다.", ex);
        } catch (SQLException ex) {
            throw new SQLException("데이터베이스 연결 중 오류 발생: " + ex.getMessage(), ex);
        }

        return conn;
    }
}
