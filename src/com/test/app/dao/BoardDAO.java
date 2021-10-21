package com.test.app.dao;

import com.test.app.dto.BoardDTO;
import com.test.common.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BoardDAO {

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public BoardDAO() {
        try {
            conn = DBUtil.connection();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("MemberDAO, DB커넥션 예외");
        }
    }


    //List 서블릿 - 게시판 목록을 보여주기
    public ArrayList<BoardDTO> list(){
        try{
            String sql = "SELECT * FROM board ORDER BY seq DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // 옮겨 담을 큰 상자
            ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();

            while (rs.next()) {
                //레코드 1줄 > BoardDTO 1개
                BoardDTO dto = new BoardDTO();

                dto.setSeq(rs.getString("seq"));
//                dto.setId(rs.getString("id"));
                dto.setName(rs.getString("name"));
                dto.setSubject(rs.getString("subject"));
                dto.setReadcount(rs.getString("readcount"));
                dto.setIsnew(rs.getString("isnew")); //글쓰고 난 뒤 며칠이 지났는지 시간

                list.add(dto);
            }

            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
