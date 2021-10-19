package com.test.common;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    public static Connection connection(){

        Connection conn = null;

        String url = "jdbc:mysql://221.165.223.113:3006/servlet_test?serverTimezone=UTC";
        String id = "bizplus";
        String pw = "bizplus";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, id, pw);
            return conn;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("DB커넥션 예외");
        }

        return null;
    }
}
