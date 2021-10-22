package com.test.app.board;

import com.test.app.dao.BoardDAO;
import com.test.app.dto.BoardDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/board/addok.do")
public class AddOk extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. 데이터 가져오기
        String subject = req.getParameter("subject");
        String content = req.getParameter("content");
        String tag = req.getParameter("tag");

        // 2.  DB 작업 > DAO 위임 > insert
        BoardDAO dao = new BoardDAO();
        BoardDTO dto = new BoardDTO();

        // 로그인한 아이디를 가져오기 위해 session을 가져온다.
        HttpSession session = req.getSession();

        dto.setId(session.getAttribute("id").toString());
        dto.setSubject(subject);
        dto.setContent(content);
        dto.setTag(tag);

        int result = dao.add(dto);

        // 3. 결과 > 후처리
        if ( result == 1 ) {
            resp.sendRedirect("/myapp/board/list.do");
        } else {
            System.out.println(result);
            resp.sendRedirect("/myapp/board/add.do");
        }

    }
}
