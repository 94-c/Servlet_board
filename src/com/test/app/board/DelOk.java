package com.test.app.board;

import com.test.app.dao.BoardDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/board/delok.do")
public class DelOk extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. 데이터 가져오기(seq)
        String seq = req.getParameter("seq");

        // 2. DB 작업 > DAO 위임 > delete
        BoardDAO dao = new BoardDAO();

        HttpSession session = req.getSession();

        int result = dao.del(seq);
        
        // 2.5 현재 글에 달린 댓글부터 삭제하기[***]
        dao.delAllComment(seq); // 부모 글번호

        // 3. 결과 후 처리
        // 수정 할 글번호(seq) 가지고 넘어가기
        if (result == 1){
            resp.sendRedirect("/myapp/board/list.do");
        }else {
            resp.sendRedirect("/myapp/board/del.do?seq=" + seq);
        }
    }
}
