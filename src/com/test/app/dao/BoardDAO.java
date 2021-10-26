package com.test.app.dao;

import com.test.app.dto.BoardDTO;
import com.test.app.dto.CommentDTO;
import com.test.common.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

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
    public ArrayList<BoardDTO> list(HashMap<String, String> map) {

        try {

            String where ="";

            if ( map.get("isSearch").equals("y") ) {
                // 검색
                // where name like '%홍길동%'
                // where subject like '%날씨%'
                // where all like '%날씨%'

                if ( map.get("column").equals("all") ) {
                    where = String.format(" where subject like '%s%%' or content like '%%%s%%' "
                            , map.get("search"), map.get("search"));
                } else {
                    where = String.format(" where %s like '%%%s%%' "
                            , map.get("column"), map.get("search"));
                }

            }
            String sql = String.format("SELECT * FROM tblboards INNER JOIN tblusers ON tblboards.id = tblusers.id  %s order by seq desc", where);
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
                //dto.setCcnt(rs.getString("ccnt"));  // 현재 글에 달린 댓글 갯수


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

    // EditOk 서블릿이 수정할 DTO를 줄테니 update 해주세요!
    public int edit(BoardDTO dto) {
        try {
            String sql = "UPDATE tblBoards SET subject=?, content=?, tag=? WHERE seq=?";

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, dto.getSubject());
            pstmt.setString(2, dto.getContent());
            pstmt.setString(3, dto.getTag());
            pstmt.setString(4, dto.getSeq());

            return pstmt.executeUpdate(); // 성공시 1 실패시 0

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //DelOk 서블릿이 글번호를 줄테니 글을 삭제해주세요!
    public int del(String seq){
        try{
            String sql = "DELETE FROM tblBoards WHERE seq = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, seq);

            return pstmt.executeUpdate(); // 성공시 1, 실패시 0
        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }


    // 댓글
    public ArrayList<CommentDTO> listComment(String seq) {

        try {

            // 부모글 번호를 조건으로 받기
            String sql = "select c.*, (select name from tblUsers where id = c.id) as name "
                    + "from tblComment c where pseq = ? order by seq asc";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, seq);

            rs = pstmt.executeQuery();

            ArrayList<CommentDTO> clist = new ArrayList<CommentDTO>();

            while ( rs.next() ) {

                CommentDTO dto = new CommentDTO();

                dto.setSeq(rs.getString("seq"));
                dto.setContent(rs.getString("content"));
                dto.setId(rs.getString("id"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setPseq(rs.getString("pseq"));
                dto.setName(rs.getString("name"));

                clist.add(dto);
            }
            return clist;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //댓글 등록
    public int addComment(CommentDTO dto) {

        try {

            String sql = "insert into tblComment (id, content, regdate, pseq) values ( ?, ?, default, ?)";

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, dto.getId());
            pstmt.setString(2, dto.getContent());
            pstmt.setString(3, dto.getPseq());

            return pstmt.executeUpdate(); // 성공시 1 실패시 0

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

}