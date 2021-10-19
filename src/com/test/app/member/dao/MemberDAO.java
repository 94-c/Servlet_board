package com.test.app.member.dao;

import com.test.app.member.dto.MemberDTO;
import com.test.common.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberDAO {

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public MemberDAO() {
        try {
            conn = DBUtil.connection();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("MemberDAO, DB커넥션 예외");
        }
    }

    public MemberDTO login(MemberDTO dto) {

        try {

            String sql = "select * from tblUsers where id=? and pw=?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getId());
            pstmt.setString(2, dto.getPw());

            rs = pstmt.executeQuery();

            if ( rs.next() ) {
                // 있으면 객체(DTO) 반환
                MemberDTO result = new MemberDTO();

                result.setId(rs.getString("id"));
                result.setName(rs.getString("name"));
                result.setLv(rs.getString("lv"));
                result.setRegdate(rs.getString("regdate"));

                return result;
            }
            // 없으면 null 반환

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
