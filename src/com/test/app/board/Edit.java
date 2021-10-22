package com.test.app.board;

import com.test.app.dao.BoardDAO;
import com.test.app.dto.BoardDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/edit.do")
public class Edit extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. 데이터 가져오기
        String seq = req.getParameter("seq");

        // 2. DB작업 > DAO 위임 > SELECT WHERE seq
        BoardDAO dao = new BoardDAO();

        BoardDTO dto = dao.get(seq);

        // 3. BoardDTO 반환 > JSP 호출 + 전달
        req.setAttribute("dto", dto);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/board/edit.jsp");
        dispatcher.forward(req, resp);
    }
}
