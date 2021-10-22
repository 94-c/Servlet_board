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


    // List 서블릿 - 게시판 목록을 보여주기
    public ArrayList<BoardDTO> list() {

        try {

            String sql = "select * from tblboards inner join tblusers on tblboards.id = tblusers.id order by seq desc";

            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            // 옮겨 담을 큰상자
            ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();

            while ( rs.next() ) {

                // 레코드 1줄 -> BoardDTO 1개
                BoardDTO dto = new BoardDTO();

                dto.setSeq(rs.getString("seq"));
                // dto.setId(rs.getString("id"));
                dto.setName(rs.getString("name"));
                dto.setSubject(rs.getString("subject"));
                dto.setReadcount(rs.getString("readcount"));
                dto.setRegdate(rs.getString("regdate"));
                // dto.setIsnew(rs.getString("isnew")); // 글쓰고 난뒤 며칠이 지났는지 시간

                list.add(dto);
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    //AddOk 서블릿 DTO를 줄테니, insert를 해주세요!
    public int add(BoardDTO dto) {

        try {
            String sql = "insert into tblboards (id, subject, content, regdate, readcount, tag)"
                    + " values (?, ?, ?, default, default, ?)";

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, dto.getId());
            pstmt.setString(2, dto.getSubject());
            pstmt.setString(3, dto.getContent());
            pstmt.setString(4, dto.getTag());

            return pstmt.executeUpdate(); // 성공시 1 실패시 0

        } catch (Exception e) {
            e.printStackTrace();
        }


        return 0;
    }

    //View 서블릿이 글번호를 줄테니 레코드 내용 전부를 DTO에 담아서 돌려주세요.
    public BoardDTO get(String seq){
        try{

            String sql = "SELECT * FROM tblboards INNER JOIN tblusers ON tblboards.id = tblusers.id  WHERE seq = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, seq);

            rs = pstmt.executeQuery();

            if (rs.next()){
                BoardDTO dto = new BoardDTO();

                dto.setSeq(rs.getString("seq"));
                dto.setSubject(rs.getString("subject"));
                dto.setContent(rs.getString("content"));
                dto.setId(rs.getString("id"));
                dto.setName(rs.getString("name"));
                dto.setReadcount(rs.getString("readcount"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setTag(rs.getString("tag"));

                return dto;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //View 서블릿이 글번호를 줄테니 조회수를 +1 해주세요!
    public void updateReadCount(String seq){
        try{
            String sql = "UPDATE tblboards SET readcount = readcount + 1 WHERE seq = ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, seq);

            pstmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}